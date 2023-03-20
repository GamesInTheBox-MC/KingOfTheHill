package me.hsgamer.gamesinthebox.kingofthehill.state;

import me.hsgamer.gamesinthebox.game.feature.BoundingFeature;
import me.hsgamer.gamesinthebox.game.feature.PointFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimplePointFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleUpdateFeature;
import me.hsgamer.gamesinthebox.kingofthehill.GameUtil;
import me.hsgamer.gamesinthebox.kingofthehill.KingOfTheHill;
import me.hsgamer.gamesinthebox.kingofthehill.feature.CooldownFeature;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.extra.ColoredDisplayName;
import me.hsgamer.minigamecore.implementation.feature.TimerFeature;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InGameState implements GameState, ColoredDisplayName {
    private final KingOfTheHill expansion;

    public InGameState(KingOfTheHill expansion) {
        this.expansion = expansion;
    }

    @Override
    public void start(Arena arena) {
        String startMessage = GameUtil.replace(expansion.getMessageConfig().getStartBroadcast(), arena);
        Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.sendMessage(player, startMessage));
        arena.getFeature(CooldownFeature.class).start(this);
    }

    @Override
    public void update(Arena arena) {
        if (arena.getFeature(TimerFeature.class).getDuration() <= 0) {
            arena.setNextState(EndingState.class);
            return;
        }
        BoundingFeature boundingFeature = arena.getFeature(BoundingFeature.class);
        SimplePointFeature pointFeature = arena.getFeature(SimplePointFeature.class);
        arena.getFeature(PointFeature.class).resetPointIfNotOnline();
        List<UUID> playersToAdd = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            UUID uuid = player.getUniqueId();
            if (!player.isDead() && boundingFeature.checkBounding(uuid)) {
                playersToAdd.add(uuid);
            } else {
                pointFeature.removePoint(uuid);
            }
        }
        if (!playersToAdd.isEmpty()) {
            pointFeature.tryAddPoint(playersToAdd);
        }

        arena.getFeature(SimpleUpdateFeature.class).updateState();
    }

    @Override
    public String getDisplayName() {
        return expansion.getMessageConfig().getStateInGame();
    }
}
