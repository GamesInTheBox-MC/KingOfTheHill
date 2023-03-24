package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.simple.SimpleGame;
import me.hsgamer.gamesinthebox.game.simple.SimpleGameEditor;
import me.hsgamer.gamesinthebox.kingofthehill.state.EndingState;
import me.hsgamer.gamesinthebox.kingofthehill.state.IdlingState;
import me.hsgamer.gamesinthebox.kingofthehill.state.InGameState;
import me.hsgamer.gamesinthebox.kingofthehill.state.WaitingState;
import me.hsgamer.gamesinthebox.planner.Planner;
import me.hsgamer.minigamecore.base.GameState;

import java.util.Arrays;
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
    protected SimpleGameEditor createEditor() {
        return new KOTHGameEditor(this);
    }

    @Override
    public String getDisplayName() {
        return expansion.getMessageConfig().getDisplayName();
    }

    @Override
    protected List<GameState> loadGameStates() {
        return Arrays.asList(
                new IdlingState(expansion),
                new WaitingState(expansion),
                new InGameState(expansion),
                new EndingState(expansion)
        );
    }
}
