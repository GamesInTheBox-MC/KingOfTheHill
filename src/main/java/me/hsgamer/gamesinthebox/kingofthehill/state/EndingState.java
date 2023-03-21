package me.hsgamer.gamesinthebox.kingofthehill.state;

import me.hsgamer.gamesinthebox.game.feature.PlannerFeature;
import me.hsgamer.gamesinthebox.game.feature.PointFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleRewardFeature;
import me.hsgamer.gamesinthebox.kingofthehill.KingOfTheHill;
import me.hsgamer.gamesinthebox.kingofthehill.feature.CooldownFeature;
import me.hsgamer.gamesinthebox.util.GameUtil;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.extra.ColoredDisplayName;
import me.hsgamer.minigamecore.implementation.feature.TimerFeature;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.UUID;

public class EndingState implements GameState, ColoredDisplayName {
    private final KingOfTheHill expansion;

    public EndingState(KingOfTheHill expansion) {
        this.expansion = expansion;
    }

    @Override
    public void start(Arena arena) {
        String endMessage = GameUtil.replaceName(expansion.getMessageConfig().getEndBroadcast(), arena);
        Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.sendMessage(player, endMessage));

        List<UUID> topList = arena.getFeature(PointFeature.class).getTopUUID();
        if (!arena.getFeature(SimpleRewardFeature.class).tryReward(topList)) {
            String notEnoughPlayerMessage = GameUtil.replaceName(expansion.getMessageConfig().getNotEnoughPlayerToReward(), arena);
            Bukkit.getOnlinePlayers().forEach(player -> MessageUtils.sendMessage(player, notEnoughPlayerMessage));
        }

        arena.getFeature(CooldownFeature.class).start(this);
    }

    @Override
    public void update(Arena arena) {
        if (arena.getFeature(TimerFeature.class).getDuration() <= 0) {
            arena.setNextState(IdlingState.class);
        }
    }

    @Override
    public void end(Arena arena) {
        arena.getFeature(PlannerFeature.class).notifyFinished();
    }

    @Override
    public String getDisplayName() {
        return expansion.getMessageConfig().getStateEnding();
    }
}
