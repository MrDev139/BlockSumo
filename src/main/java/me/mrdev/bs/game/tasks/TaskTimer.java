package me.mrdev.bs.game.tasks;

import me.mrdev.bs.BlocksSumo;
import org.bukkit.scheduler.BukkitTask;

public abstract class TaskTimer {

    private final BlocksSumo plugin;
    private long delay = 20;
    private BukkitTask task;

    public TaskTimer(BlocksSumo plugin) {
        this.plugin = plugin;
    }

    public TaskTimer(BlocksSumo plugin , long delay) {
        this.plugin = plugin;
        this.delay = delay;
    }

    public abstract void execute();

    public void start() {
        task = plugin.getServer().getScheduler().runTaskTimer(plugin , this::execute, delay , delay);
    }

    protected void cancel() {
        plugin.getServer().getScheduler().cancelTask(task.getTaskId());
    }

    public boolean isStarted() {
        return task != null;
    }

    public long getDelay() {
        return delay;
    }
}
