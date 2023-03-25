package me.hsgamer.gamesinthebox.kingofthehill.feature;

import me.hsgamer.gamesinthebox.game.feature.GameConfigFeature;
import me.hsgamer.gamesinthebox.kingofthehill.KOTHGameArena;
import me.hsgamer.gamesinthebox.kingofthehill.state.EndingState;
import me.hsgamer.gamesinthebox.kingofthehill.state.InGameState;
import me.hsgamer.gamesinthebox.kingofthehill.state.WaitingState;
import me.hsgamer.gamesinthebox.util.TimeUtil;
import me.hsgamer.minigamecore.base.Feature;
import me.hsgamer.minigamecore.base.GameState;
import me.hsgamer.minigamecore.implementation.feature.TimerFeature;

import java.util.Optional;

public class CooldownFeature implements Feature {
    private final KOTHGameArena arena;
    private long waitingTime = 60000L;
    private long inGameTime = 300000L;
    private long endingTime = 10000L;
    private boolean canStart = false;

    public CooldownFeature(KOTHGameArena arena) {
        this.arena = arena;
    }

    @Override
    public void postInit() {
        GameConfigFeature configFeature = arena.getFeature(GameConfigFeature.class);

        waitingTime = Optional.ofNullable(configFeature.getString("time.waiting"))
                .map(TimeUtil::parseMillis)
                .orElse(waitingTime);
        inGameTime = Optional.ofNullable(configFeature.getString("time.in-game"))
                .map(TimeUtil::parseMillis)
                .orElse(inGameTime);
        endingTime = Optional.ofNullable(configFeature.getString("time.ending"))
                .map(TimeUtil::parseMillis)
                .orElse(endingTime);
    }

    public void start(GameState gameState) {
        TimerFeature timerFeature = arena.getFeature(TimerFeature.class);
        if (gameState instanceof WaitingState) {
            timerFeature.setDuration(waitingTime);
        } else if (gameState instanceof InGameState) {
            timerFeature.setDuration(inGameTime);
        } else if (gameState instanceof EndingState) {
            timerFeature.setDuration(endingTime);
        }
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public boolean canStart() {
        return canStart;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public long getInGameTime() {
        return inGameTime;
    }

    public long getEndingTime() {
        return endingTime;
    }
}
