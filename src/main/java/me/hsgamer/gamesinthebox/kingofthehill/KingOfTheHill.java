package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.template.*;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class KingOfTheHill extends TemplateGameExpansion {
    private final KOTHMessageConfig messageConfig = ConfigGenerator.newInstance(KOTHMessageConfig.class, new BukkitConfig(new File(getDataFolder(), "messages.yml")));

    @Override
    public TemplateGameArenaLogic createArenaLogic(TemplateGameArena arena) {
        return new KOTHGameLogic(this, arena);
    }

    @Override
    public TemplateGameEditor getEditor(TemplateGame game) {
        return new KOTHGameEditor(game);
    }

    @Override
    public String getDisplayName() {
        return messageConfig.getDisplayName();
    }

    @Override
    public List<String> getDefaultHologramLines(String name) {
        return Optional.ofNullable(messageConfig.getDefaultHologramLines().get(name))
                .map(CollectionUtils::createStringListFromObject)
                .orElseGet(() -> super.getDefaultHologramLines(name));
    }

    @Override
    protected String @NotNull [] getGameType() {
        return new String[]{"king-of-the-hill", "koth"};
    }

    public KOTHMessageConfig getMessageConfig() {
        return messageConfig;
    }

    @Override
    public void onReload() {
        super.onReload();
        messageConfig.reloadConfig();
    }
}
