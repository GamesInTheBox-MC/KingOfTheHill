package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.simple.SimpleGame;
import me.hsgamer.gamesinthebox.planner.Planner;
import me.hsgamer.minigamecore.base.GameState;

import java.util.Collections;
import java.util.List;

public class KOTHGame extends SimpleGame {
    private final KingOfTheHill expansion;

    public KOTHGame(KingOfTheHill expansion) {
        this.expansion = expansion;
    }

    @Override
    protected KOTHGameArena newArena(String name, Planner planner) {
        return new KOTHGameArena(expansion, name, this, planner);
    }

    @Override
    public String getDisplayName() {
        return expansion.getConfig().getDisplayName();
    }

    @Override
    protected List<GameState> loadGameStates() {
        return Collections.emptyList();
    }
}
