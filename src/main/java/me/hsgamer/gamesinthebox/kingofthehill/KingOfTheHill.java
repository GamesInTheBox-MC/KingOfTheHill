package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.expansion.SingleGameExpansion;
import me.hsgamer.gamesinthebox.game.Game;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;

import java.io.File;

public class KingOfTheHill extends SingleGameExpansion {
    private final KOTHConfig config = ConfigGenerator.newInstance(KOTHConfig.class, new BukkitConfig(new File(getDataFolder(), "config.yml")));

    @Override
    protected Game getGame() {
        return new KOTHGame(this);
    }

    @Override
    protected String[] getGameType() {
        return new String[]{"king-of-the-hill", "koth"};
    }

    public KOTHConfig getConfig() {
        return config;
    }
}
