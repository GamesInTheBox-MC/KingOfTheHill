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
import me.hsgamer.gamesinthebox.planner.feature.VariableFeature;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.minigamecore.base.Feature;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class KOTHGameLogic extends TemplateGameArenaLogic {
    private final KingOfTheHill expansion;
    private int minPlayersToAddPoint = -1;

    public KOTHGameLogic(KingOfTheHill expansion, TemplateGameArena arena) {
        super(arena);
        this.expansion = expansion;
    }

    public int getMinPlayersToAddPoint() {
        return minPlayersToAddPoint;
    }

    @Override
    public List<Feature> loadFeatures() {
        return Arrays.asList(
                new SimpleBoundingFeature(arena, true),
                new ParticleTaskFeature(arena),
                new SimpleParticleFeature(arena)
        );
    }

    @Override
    public void postInit() {
        minPlayersToAddPoint = Optional.ofNullable(arena.getFeature(GameConfigFeature.class))
                .map(gameConfigFeature -> gameConfigFeature.getString("min-players-to-add-point"))
                .flatMap(Validate::getNumber)
                .map(Number::intValue)
                .orElse(minPlayersToAddPoint);
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
        List<UUID> playersToAdd = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            if (!player.isDead() && boundingFeature.checkBounding(player)) {
                playersToAdd.add(uuid);
            } else {
                pointFeature.applyPoint(uuid, KingOfTheHill.POINT_MINUS);
            }
        }
        if (!playersToAdd.isEmpty() && (minPlayersToAddPoint < 0 || playersToAdd.size() >= minPlayersToAddPoint)) {
            pointFeature.applyPoint(playersToAdd, KingOfTheHill.POINT_PLUS);
        }
    }

    @Override
    public void onInGameOver() {
        arena.getFeature(ParticleTaskFeature.class).stop();
    }

    @Override
    public void onEndingStart() {
        List<UUID> topList = arena.getFeature(PointFeature.class).getTopUUID();
        if (!arena.getFeature(SimpleRewardFeature.class).tryReward(topList)) {
            String notEnoughPlayerMessage = arena.getFeature(VariableFeature.class).replace(expansion.getMessageConfig().getNotEnoughPlayerToReward());
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.sendMessage(player, notEnoughPlayerMessage));
        }
    }

    @Override
    public void forceEnd() {
        arena.getFeature(ParticleTaskFeature.class).stop();
    }
}
