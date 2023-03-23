package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.expansion.SingleGameExpansion;
import me.hsgamer.gamesinthebox.expansion.extra.Reloadable;
import me.hsgamer.gamesinthebox.game.Game;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;

import java.io.File;

public class KingOfTheHill extends SingleGameExpansion implements Reloadable {
    private final KOTHMainConfig mainConfig = ConfigGenerator.newInstance(KOTHMainConfig.class, new BukkitConfig(new File(getDataFolder(), "config.yml")));
    private final KOTHMessageConfig messageConfig = ConfigGenerator.newInstance(KOTHMessageConfig.class, new BukkitConfig(new File(getDataFolder(), "messages.yml")));

    @Override
    protected Game getGame() {
        return new KOTHGame(this);
    }

    @Override
    protected String[] getGameType() {
        return new String[]{"king-of-the-hill", "koth"};
    }

    public KOTHMainConfig getMainConfig() {
        return mainConfig;
    }

    public KOTHMessageConfig getMessageConfig() {
        return messageConfig;
    }

    @Override
    public void onReload() {
        mainConfig.reloadConfig();
        messageConfig.reloadConfig();
    }
}
