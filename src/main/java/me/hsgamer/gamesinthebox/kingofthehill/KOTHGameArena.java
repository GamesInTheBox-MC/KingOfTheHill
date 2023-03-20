package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.game.simple.SimpleGameArena;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleBoundingFeature;
import me.hsgamer.gamesinthebox.planner.Planner;
import me.hsgamer.gamesinthebox.util.ActionBarUtil;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.minigamecore.base.Feature;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class KOTHGameArena extends SimpleGameArena {
    private final KingOfTheHill expansion;

    public KOTHGameArena(KingOfTheHill expansion, String name, KOTHGame game, Planner planner) {
        super(name, game, planner);
        this.expansion = expansion;
    }

    @Override
    protected void onPointChanged(UUID uuid, int point, int totalPoints) {
        if (point == 0) return;

        String message = point < 0 ? expansion.getMessageConfig().getPointMinus() : expansion.getMessageConfig().getPointPlus();
        int absPoint = Math.abs(point);

        String finalMessage = message
                .replace("{point}", Integer.toString(absPoint))
                .replace("{total}", Integer.toString(totalPoints));

        ActionBarUtil.sendActionBar(uuid, finalMessage);
    }

    @Override
    protected List<Feature> loadFeatures() {
        List<Feature> features = super.loadFeatures();
        features.add(new SimpleBoundingFeature(this, true));
        return features;
    }

    @Override
    protected List<String> getDefaultHologramLines(String name) {
        return Optional.ofNullable(expansion.getMessageConfig().getDefaultHologramLines().get(name))
                .map(CollectionUtils::createStringListFromObject)
                .orElseGet(() -> super.getDefaultHologramLines(name));
    }

    @Override
    public void start() {

    }

    @Override
    public void forceEnd() {

    }

    @Override
    public long getDelay() {
        return expansion.getMainConfig().getGameInterval();
    }

    @Override
    public long getPeriod() {
        return expansion.getMainConfig().getGameInterval();
    }

    @Override
    public boolean isAsync() {
        return expansion.getMainConfig().isGameAsync();
    }
}
