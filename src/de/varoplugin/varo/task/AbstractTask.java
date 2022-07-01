package de.varoplugin.varo.task;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractTask implements VaroRegistrable, Runnable {

    private BukkitRunnable runnable;

    private BukkitRunnable createRunnable() {
        return this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                AbstractTask.this.run();
            }
        };
    }

    protected abstract void schedule(BukkitRunnable runnable);

    @Override
    public void register() {
        this.schedule(this.runnable = this.createRunnable());
    }

    @Override
    public void deregister() {
        this.runnable.cancel();
        this.runnable = null;
    }

    @Override
    public boolean isRegistered() {
        return this.runnable != null;
    }
}
