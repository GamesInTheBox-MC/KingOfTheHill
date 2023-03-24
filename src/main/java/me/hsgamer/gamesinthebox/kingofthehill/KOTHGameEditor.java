package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.simple.SimpleGameEditor;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import org.bukkit.command.CommandSender;

import java.util.*;

public class KOTHGameEditor extends SimpleGameEditor {
    private final SimpleBoundingFeature.Editor boundingFeatureEditor = SimpleBoundingFeature.editor();
    private String waitingTime;
    private String inGameTime;
    private String endingTime;

    public KOTHGameEditor(KOTHGame game) {
        super(game);
    }

    @Override
    protected Map<String, SimpleAction> createActionMap() {
        Map<String, SimpleAction> map = super.createActionMap();

        map.putAll(boundingFeatureEditor.getActions());

        // COOLDOWN TIME
        map.put("set-waiting-time", new SimpleAction() {
            @Override
            public String getDescription() {
                return "Set the waiting time";
            }

            @Override
            public String getArgsUsage() {
                return "<time>";
            }

            @Override
            public List<String> getActionArgs(CommandSender sender, String... args) {
                if (args.length == 1) {
                    return Arrays.asList("1s", "1m", "1h");
                }
                return Collections.emptyList();
            }

            @Override
            public boolean performAction(CommandSender sender, String... args) {
                if (args.length >= 1) {
                    waitingTime = args[0];
                    return true;
                }
                return false;
            }
        });
        map.put("set-in-game-time", new SimpleAction() {
            @Override
            public String getDescription() {
                return "Set the in-game time";
            }

            @Override
            public String getArgsUsage() {
                return "<time>";
            }

            @Override
            public List<String> getActionArgs(CommandSender sender, String... args) {
                if (args.length == 1) {
                    return Arrays.asList("1s", "1m", "1h");
                }
                return Collections.emptyList();
            }

            @Override
            public boolean performAction(CommandSender sender, String... args) {
                if (args.length >= 1) {
                    inGameTime = args[0];
                    return true;
                }
                return false;
            }
        });
        map.put("set-ending-time", new SimpleAction() {
            @Override
            public String getDescription() {
                return "Set the ending time";
            }

            @Override
            public String getArgsUsage() {
                return "<time>";
            }

            @Override
            public List<String> getActionArgs(CommandSender sender, String... args) {
                if (args.length == 1) {
                    return Arrays.asList("1s", "1m", "1h");
                }
                return Collections.emptyList();
            }

            @Override
            public boolean performAction(CommandSender sender, String... args) {
                if (args.length >= 1) {
                    endingTime = args[0];
                    return true;
                }
                return false;
            }
        });

        return map;
    }

    @Override
    protected List<SimpleEditorStatus> createEditorStatusList() {
        List<SimpleEditorStatus> list = super.createEditorStatusList();

        list.add(boundingFeatureEditor.getStatus());
        list.add(new SimpleEditorStatus() {
            @Override
            public void sendStatus(CommandSender sender) {
                MessageUtils.sendMessage(sender, "&6&lCOOLDOWN TIME");
                MessageUtils.sendMessage(sender, "&6Waiting time: &e" + (waitingTime == null ? "Default" : waitingTime));
                MessageUtils.sendMessage(sender, "&6In-game time: &e" + (inGameTime == null ? "Default" : inGameTime));
                MessageUtils.sendMessage(sender, "&6Ending time: &e" + (endingTime == null ? "Default" : endingTime));
            }

            @Override
            public void reset(CommandSender sender) {
                waitingTime = null;
                inGameTime = null;
                endingTime = null;
            }

            @Override
            public boolean canSave(CommandSender sender) {
                return true;
            }

            @Override
            public Map<String, Object> toPathValueMap(CommandSender sender) {
                Map<String, Object> map = new LinkedHashMap<>();
                if (waitingTime != null) {
                    map.put("time.waiting", waitingTime);
                }
                if (inGameTime != null) {
                    map.put("time.in-game", inGameTime);
                }
                if (endingTime != null) {
                    map.put("time.ending", endingTime);
                }
                return map;
            }
        });

        return list;
    }
}
