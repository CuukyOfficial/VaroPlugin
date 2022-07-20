package de.varoplugin.varo.api.task;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class ScheduledTask extends RunnableTask implements Runnable {

    private BukkitRunnable runnable;

    public ScheduledTask(Plugin plugin) {
       super(plugin);
    }

    private BukkitRunnable createRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                ScheduledTask.this.run();
            }
        };
    }

    protected abstract void schedule(BukkitRunnable runnable);

    @Override
    public void onEnable() {
        this.schedule(this.runnable = this.createRunnable());
    }

    @Override
    public void onDisable() {
        this.runnable.cancel();
        this.runnable = null;
    }
}
