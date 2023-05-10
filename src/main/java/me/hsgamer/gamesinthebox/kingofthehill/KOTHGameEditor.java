package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.GameArena;
import me.hsgamer.gamesinthebox.game.simple.action.NumberAction;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleParticleFeature;
import me.hsgamer.gamesinthebox.game.template.TemplateGame;
import me.hsgamer.gamesinthebox.game.template.TemplateGameEditor;
import me.hsgamer.gamesinthebox.game.template.feature.ArenaLogicFeature;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KOTHGameEditor extends TemplateGameEditor {
    private final SimpleBoundingFeature.Editor boundingFeatureEditor = SimpleBoundingFeature.editor(true);
    private final SimpleParticleFeature.Editor particleFeatureEditor = SimpleParticleFeature.editor();
    private int minPlayersToAddPoint = -1;

    public KOTHGameEditor(@NotNull TemplateGame game) {
        super(game);
    }

    @Override
    protected @NotNull Map<String, SimpleAction> createActionMap() {
        Map<String, SimpleAction> map = super.createActionMap();
        map.putAll(boundingFeatureEditor.getActions());
        map.putAll(particleFeatureEditor.getActions());
        map.put("set-min-players-to-add-point", new NumberAction() {
            @Override
            protected boolean performAction(@NotNull CommandSender sender, @NotNull Number value, String... args) {
                minPlayersToAddPoint = value.intValue();
                return true;
            }

            @Override
            public @NotNull String getDescription() {
                return "Set the minimum players to add point";
            }

            @Override
            protected @NotNull List<String> getValueArgs(@NotNull CommandSender sender, String... args) {
                return Arrays.asList("-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            }
        });
        return map;
    }

    @Override
    protected @NotNull List<SimpleEditorStatus> createEditorStatusList() {
        List<SimpleEditorStatus> list = super.createEditorStatusList();
        list.add(boundingFeatureEditor.getStatus());
        list.add(particleFeatureEditor.getStatus());
        list.add(new SimpleEditorStatus() {
            @Override
            public void sendStatus(@NotNull CommandSender sender) {
                MessageUtils.sendMessage(sender, "&6&lKOTH Game Editor");
                MessageUtils.sendMessage(sender, "&eMin players to add point: &6" + minPlayersToAddPoint);
            }

            @Override
            public void reset(@NotNull CommandSender sender) {
                minPlayersToAddPoint = -1;
            }

            @Override
            public boolean canSave(@NotNull CommandSender sender) {
                return true;
            }

            @Override
            public Map<String, Object> toPathValueMap(@NotNull CommandSender sender) {
                return Collections.singletonMap("min-players-to-add-point", minPlayersToAddPoint);
            }
        });
        return list;
    }

    @Override
    public boolean migrate(@NotNull CommandSender sender, @NotNull GameArena gameArena) {
        Optional.ofNullable(gameArena.getFeature(SimpleBoundingFeature.class)).ifPresent(boundingFeatureEditor::migrate);
        Optional.ofNullable(gameArena.getFeature(SimpleParticleFeature.class)).ifPresent(particleFeatureEditor::migrate);
        Optional.ofNullable(gameArena.getFeature(ArenaLogicFeature.class))
                .map(ArenaLogicFeature::getArenaLogic)
                .filter(KOTHGameLogic.class::isInstance)
                .map(KOTHGameLogic.class::cast)
                .ifPresent(logic -> minPlayersToAddPoint = logic.getMinPlayersToAddPoint());
        return super.migrate(sender, gameArena);
    }
}
