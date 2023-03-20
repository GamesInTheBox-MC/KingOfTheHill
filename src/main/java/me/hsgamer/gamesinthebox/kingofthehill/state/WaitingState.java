package me.hsgamer.gamesinthebox.kingofthehill.state;

import me.hsgamer.gamesinthebox.game.simple.feature.SimpleUpdateFeature;
import me.hsgamer.gamesinthebox.kingofthehill.KingOfTheHill;
import me.hsgamer.gamesinthebox.kingofthehill.feature.CooldownFeature;
import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.extra.ColoredDisplayName;
import me.hsgamer.minigamecore.implementation.feature.TimerFeature;

public class WaitingState implements GameState, ColoredDisplayName {
    private final KingOfTheHill expansion;

    public WaitingState(KingOfTheHill expansion) {
        this.expansion = expansion;
    }

    @Override
    public void start(Arena arena) {
        arena.getFeature(CooldownFeature.class).start(this);
        arena.getFeature(SimpleUpdateFeature.class).initState();
    }

    @Override
    public void update(Arena arena) {
        if (arena.getFeature(TimerFeature.class).getDuration() <= 0) {
            arena.setNextState(InGameState.class);
        }
    }

    @Override
    public String getDisplayName() {
        return expansion.getMessageConfig().getStateWaiting();
    }
}
