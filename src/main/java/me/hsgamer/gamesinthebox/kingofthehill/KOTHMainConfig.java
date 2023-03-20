package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface KOTHMainConfig {
    @ConfigPath("game.interval")
    default long getGameInterval() {
        return 20;
    }

    @ConfigPath("game.async")
    default boolean isGameAsync() {
        return true;
    }
}
