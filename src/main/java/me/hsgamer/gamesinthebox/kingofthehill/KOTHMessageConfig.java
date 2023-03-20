package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.hscore.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public interface KOTHMessageConfig {
    @ConfigPath("display-name")
    default String getDisplayName() {
        return "King of the Hill";
    }

    @ConfigPath("point.plus")
    default String getPointPlus() {
        return "&a+{point} point(s) &7({total})";
    }

    @ConfigPath("point.minus")
    default String getPointMinus() {
        return "&c-{point} point(s) &7({total})";
    }

    @ConfigPath("default-hologram-lines")
    default Map<String, Object> getDefaultHologramLines() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("description", Arrays.asList(
                "&c&lKING OF THE HILL",
                "&fThere is a box in the middle of the arena",
                "&fYou need to stand on it to get points",
                "&fThe player with the most points will win"
        ));
        map.put("points", Arrays.asList(
                "&fPoints when you stand on the box: &a{game_point_plus}",
                "&fPoints when you leave the box: &c{game_point_minus}"
        ));
        map.put("top", Arrays.asList(
                "&a#1 &f{game_top_name_1} &7- &f{game_top_value_1}",
                "&a#2 &f{game_top_name_2} &7- &f{game_top_value_2}",
                "&a#3 &f{game_top_name_3} &7- &f{game_top_value_3}",
                "&a#4 &f{game_top_name_4} &7- &f{game_top_value_4}",
                "&a#5 &f{game_top_name_5} &7- &f{game_top_value_5}"
        ));
        return map;
    }

    @ConfigPath("state.waiting")
    default String getStateWaiting() {
        return "Waiting";
    }

    @ConfigPath("state.in-game")
    default String getStateInGame() {
        return "In Game";
    }

    @ConfigPath("state.ending")
    default String getStateEnding() {
        return "Ending";
    }

    @ConfigPath("state.idle")
    default String getStateIdle() {
        return "Idle";
    }

    @ConfigPath("start-broadcast")
    default String getStartBroadcast() {
        return "&aThe game has started!";
    }

    @ConfigPath("end-broadcast")
    default String getEndBroadcast() {
        return "&aThe game has ended!";
    }

    @ConfigPath("not-enough-player-to-reward")
    default String getNotEnoughPlayerToReward() {
        return "&cThere are not enough players to reward";
    }
}
