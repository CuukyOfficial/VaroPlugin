package de.varoplugin.varo.jobslegacy;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents any Varo task.
 */
public abstract class AbstractVaroTask extends AbstractVaroJob implements Runnable {

    private BukkitRunnable runnable;

    private BukkitRunnable createRunnable() {
        return this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                AbstractVaroTask.this.run();
            }
        };
    }

    protected abstract void schedule(BukkitRunnable runnable);

    @Override
    protected void doRegister() {
        this.schedule(this.runnable = this.createRunnable());
    }

    @Override
    protected void doUnregister() {
        if (this.runnable != null) this.runnable.cancel();
    }
}