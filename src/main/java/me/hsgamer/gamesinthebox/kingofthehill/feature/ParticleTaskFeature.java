package me.hsgamer.gamesinthebox.kingofthehill.feature;

import com.cryptomorin.xseries.particles.ParticleDisplay;
import com.cryptomorin.xseries.particles.XParticle;
import me.hsgamer.gamesinthebox.game.feature.BoundingFeature;
import me.hsgamer.gamesinthebox.game.feature.GameConfigFeature;
import me.hsgamer.gamesinthebox.game.simple.SimpleGameArena;
import me.hsgamer.gamesinthebox.game.simple.feature.SimpleParticleFeature;
import me.hsgamer.gamesinthebox.planner.feature.PluginFeature;
import me.hsgamer.gamesinthebox.util.TaskUtil;
import me.hsgamer.hscore.bukkit.block.BukkitBlockAdapter;
import me.hsgamer.hscore.bukkit.scheduler.Scheduler;
import me.hsgamer.hscore.bukkit.scheduler.Task;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.hscore.minecraft.block.box.BlockBox;
import me.hsgamer.hscore.minecraft.block.box.Position;
import me.hsgamer.minigamecore.base.Feature;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Optional;

public class ParticleTaskFeature implements Feature {
    private final SimpleGameArena arena;
    private double rate = 0.1;
    private long period = 0;
    private Task task;

    public ParticleTaskFeature(SimpleGameArena arena) {
        this.arena = arena;
    }

    @Override
    public void postInit() {
        GameConfigFeature configFeature = arena.getFeature(GameConfigFeature.class);
        rate = Optional.ofNullable(configFeature.getString("particle.rate"))
                .flatMap(Validate::getNumber)
                .map(Number::doubleValue)
                .orElse(rate);
        period = Optional.ofNullable(configFeature.getString("particle.period"))
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
        ParticleDisplay particleDisplay = arena.getFeature(SimpleParticleFeature.class).getParticleDisplay();
        task = Scheduler.CURRENT.runTaskTimer(arena.getPlanner().getFeature(PluginFeature.class).getPlugin(), () -> XParticle.structuredCube(start, end, rate, particleDisplay), period, period, true);
    }

    public void stop() {
        TaskUtil.cancelSafe(task);
        task = null;
    }

    @Override
    public void clear() {
        stop();
    }
}
