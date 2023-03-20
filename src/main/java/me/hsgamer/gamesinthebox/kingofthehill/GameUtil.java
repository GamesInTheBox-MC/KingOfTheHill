package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.feature.PlannerFeature;
import me.hsgamer.minigamecore.base.Arena;

public class GameUtil {
    private GameUtil() {
        // EMPTY
    }

    public static String replace(String input, Arena arena) {
        return input
                .replace("{planner}", arena.getFeature(PlannerFeature.class).getPlanner().getName())
                .replace("{arena}", arena.getName());
    }
}
