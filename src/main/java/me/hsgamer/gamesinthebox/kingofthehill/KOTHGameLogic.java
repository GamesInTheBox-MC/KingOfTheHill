package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.feature.BoundingFeature;
import me.hsgamer.gamesinthebox.game.feature.GameConfigFeature;
import me.hsgamer.gamesinthebox.game.feature.PointFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleParticleFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimplePointFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleRewardFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArena;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArenaLogic;
import me.hsgamer.gamesinthebox.kingofthehill.feature.ParticleTaskFeature;
import me.hsgamer.hscore.common.Pair;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.minigamecore.base.Feature;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class KOTHGameLogic extends TemplateGameArenaLogic {
    private int maxPlayersToAddPoint = -1;

    public KOTHGameLogic(TemplateGameArena arena) {
        super(arena);
    }

    public int getMaxPlayersToAddPoint() {
        return maxPlayersToAddPoint;
    }

    @Override
    public List<Feature> loadFeatures() {
        return Arrays.asList(
                new SimpleBoundingFeature(arena),
                new ParticleTaskFeature(arena),
                new SimpleParticleFeature(arena)
        );
    }

    @Override
    public void postInit() {
        maxPlayersToAddPoint = Optional.ofNullable(arena.getFeature(GameConfigFeature.class))
                .map(gameConfigFeature -> gameConfigFeature.getString("max-players-to-add-point"))
                .flatMap(Validate::getNumber)
                .map(Number::intValue)
                .orElse(maxPlayersToAddPoint);
    }

    @Override
    public @Nullable String replace(@NotNull String input) {
        if (input.equalsIgnoreCase("max_players_to_add_point")) {
            return String.valueOf(maxPlayersToAddPoint);
        }
        return null;
    }

    @Override
    public void onInGameStart() {
        arena.getFeature(ParticleTaskFeature.class).start();
    }

    @Override
    public void onInGameUpdate() {
        BoundingFeature boundingFeature = arena.getFeature(BoundingFeature.class);
        SimplePointFeature pointFeature = arena.getFeature(SimplePointFeature.class);
        arena.getFeature(PointFeature.class).resetPointIfNotOnline();

        List<UUID> playersToAdd = Bukkit.getOnlinePlayers()
                .stream()
                .filter(player -> !player.isDead() && boundingFeature.checkBounding(player, true))
                .map(Player::getUniqueId)
                .collect(Collectors.toList());
        if (!playersToAdd.isEmpty() && (maxPlayersToAddPoint < 0 || playersToAdd.size() <= maxPlayersToAddPoint)) {
            pointFeature.applyPoint(playersToAdd, KingOfTheHill.POINT_PLUS);
        }

        List<UUID> playersToMinus = pointFeature.getPoints()
                .map(Pair::getKey)
                .filter(uuid -> !playersToAdd.contains(uuid))
                .collect(Collectors.toList());
        pointFeature.applyPoint(playersToMinus, KingOfTheHill.POINT_MINUS);
    }

    @Override
    public void onInGameOver() {
        arena.getFeature(ParticleTaskFeature.class).stop();
    }

    @Override
    public void onEndingStart() {
        List<UUID> topList = arena.getFeature(PointFeature.class).getTopUUID().collect(Collectors.toList());
        arena.getFeature(SimpleRewardFeature.class).tryReward(topList);
    }

    @Override
    public void forceEnd() {
        arena.getFeature(ParticleTaskFeature.class).stop();
    }
}
