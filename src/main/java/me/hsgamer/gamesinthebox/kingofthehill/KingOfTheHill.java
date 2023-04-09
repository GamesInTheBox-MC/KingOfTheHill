package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.simple.feature.SimplePointFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGame;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArena;
import me.hsgamer.gamesinthebox.game.template.TemplateGameArenaLogic;
import me.hsgamer.gamesinthebox.game.template.TemplateGameEditor;
import me.hsgamer.gamesinthebox.game.template.expansion.TemplateGameExpansion;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.proxy.ConfigGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class KingOfTheHill extends TemplateGameExpansion {
    public static final SimplePointFeature.PointValue POINT_PLUS = new SimplePointFeature.PointValue("plus", 1, false);
    public static final SimplePointFeature.PointValue POINT_MINUS = new SimplePointFeature.PointValue("minus", -1, true);

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
    public List<SimplePointFeature.PointValue> getPointValues() {
        return Arrays.asList(POINT_PLUS, POINT_MINUS);
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
