package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.GameAction;
import me.hsgamer.minigamecore.implementation.feature.TimerFeature;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KOTHGameAction implements GameAction {
    private final KOTHGameArena arena;

    public KOTHGameAction(KOTHGameArena arena) {
        this.arena = arena;
    }

    @Override
    public List<String> getActions() {
        return Collections.singletonList(
                "skip-time"
        );
    }

    @Override
    public List<String> getActionArgs(CommandSender sender, String action, String... args) {
        return Collections.emptyList();
    }

    @Override
    public boolean performAction(CommandSender sender, String action, String... args) {
        if (action.equalsIgnoreCase("skip-time")) {
            arena.getFeature(TimerFeature.class).setDuration(0);
            return true;
        }
        return false;
    }
}
