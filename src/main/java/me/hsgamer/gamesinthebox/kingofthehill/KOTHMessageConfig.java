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
        map.put("status", Arrays.asList(
                "&fStatus: &a{planner_game_state}",
                "&fTime left: &a{game_time_left}"
        ));
        return map;
    }

    void reloadConfig();
}
