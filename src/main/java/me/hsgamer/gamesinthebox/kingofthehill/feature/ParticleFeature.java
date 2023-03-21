package me.hsgamer.gamesinthebox.kingofthehill.feature;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import me.hsgamer.gamesinthebox.game.feature.BoundingFeature;
import me.hsgamer.gamesinthebox.game.feature.GameConfigFeature;
import me.hsgamer.gamesinthebox.kingofthehill.KOTHGameArena;
import me.hsgamer.gamesinthebox.planner.feature.PluginFeature;
import me.hsgamer.gamesinthebox.util.Util;
import me.hsgamer.hscore.bukkit.block.BukkitBlockAdapter;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.hscore.minecraft.block.box.BlockBox;
import me.hsgamer.hscore.minecraft.block.box.Position;
import me.hsgamer.minigamecore.base.Feature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;
import java.util.Optional;

public class ParticleFeature implements Feature {
    private final KOTHGameArena arena;
    private ParticleDisplay particle = new ParticleDisplay();
    private double rate = 0.1;
    private long period = 0;
    private BukkitTask task;

    public ParticleFeature(KOTHGameArena arena) {
        this.arena = arena;
    }

    @Override
    public void postInit() {
        GameConfigFeature configFeature = arena.getFeature(GameConfigFeature.class);
        particle = ParticleDisplay.fromConfig(Util.createSection(configFeature.getValues("particle", false)));
        rate = Optional.ofNullable(configFeature.get("particle.rate"))
                .map(Objects::toString)
                .flatMap(Validate::getNumber)
                .map(Number::doubleValue)
                .orElse(rate);
        period = Optional.ofNullable(configFeature.get("particle.period"))
                .map(Objects::toString)
                .flatMap(Validate::getNumber)
                .map(Number::longValue)
                .orElse(period);
    }

    public void start() {
        BoundingFeature boundingFeature = arena.getFeature(BoundingFeature.class);
        World world = boundingFeature.getWorld();
        BlockBox box = boundingFeature.getBlockBox();
        Location start = BukkitBlockAdapter.adapt(world, new Position(box.minX, box.minY, box.minZ));
        Location end = BukkitBlockAdapter.adapt(world, new Position(box.maxX, box.maxY, box.maxZ));
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(arena.getPlanner().getFeature(PluginFeature.class).getPlugin(), () -> XParticle.cube(start, end, rate, particle), period, period);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public void clear() {
        stop();
    }
}
