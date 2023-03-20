package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.hscore.config.annotation.ConfigPath;

public interface KOTHConfig {
    @ConfigPath("display-name")
    default String getDisplayName() {
        return "King of the Hill";
    }

    @ConfigPath("game.interval")
    default long getGameInterval() {
        return 20;
    }

    @ConfigPath("game.async")
    default boolean isGameAsync() {
        return true;
    }
}
