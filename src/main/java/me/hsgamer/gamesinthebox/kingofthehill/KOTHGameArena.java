package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.simple.SimpleGameArena;
import me.hsgamer.gamesinthebox.planner.Planner;

public class KOTHGameArena extends SimpleGameArena {
    private final KingOfTheHill expansion;

    public KOTHGameArena(KingOfTheHill expansion, String name, KOTHGame game, Planner planner) {
        super(name, game, planner);
        this.expansion = expansion;
    }

    @Override
    public void start() {

    }

    @Override
    public void forceEnd() {

    }

    @Override
    public long getDelay() {
        return expansion.getConfig().getGameInterval();
    }

    @Override
    public long getPeriod() {
        return expansion.getConfig().getGameInterval();
    }

    @Override
    public boolean isAsync() {
        return expansion.getConfig().isGameAsync();
    }
}
