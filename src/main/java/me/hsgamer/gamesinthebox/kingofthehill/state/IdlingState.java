package me.hsgamer.gamesinthebox.kingofthehill.state;

import me.hsgamer.gamesinthebox.game.simple.feature.SimpleUpdateFeature;
import me.hsgamer.gamesinthebox.kingofthehill.KingOfTheHill;
import me.hsgamer.gamesinthebox.kingofthehill.feature.CooldownFeature;
import me.hsgamer.minigamecore.base.Arena;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.bukkit.extra.ColoredDisplayName;

public class IdlingState implements GameState, ColoredDisplayName {
    private final KingOfTheHill expansion;

    public IdlingState(KingOfTheHill expansion) {
        this.expansion = expansion;
    }

    @Override
    public void start(Arena arena) {
        arena.getFeature(CooldownFeature.class).setCanStart(false);
    }

    @Override
    public void update(Arena arena) {
        if (arena.getFeature(CooldownFeature.class).canStart()) {
            arena.setNextState(WaitingState.class);
        }
    }

    @Override
    public void end(Arena arena) {
        arena.getFeature(SimpleUpdateFeature.class).clearState();
    }

    @Override
    public String getDisplayName() {
        return expansion.getMessageConfig().getStateIdle();
    }
}
