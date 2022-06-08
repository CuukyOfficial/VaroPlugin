package de.varoplugin.varo.tasks;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents any Varo task.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractVaroTask extends AbstractVaroListener implements VaroTask, Runnable {

    private BukkitRunnable runnable;

    protected BukkitRunnable createRunnable() {
        return this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                AbstractVaroTask.this.run();
            }
        };
    }

    /**
     * Override this to schedule the task
     */
    protected void schedule() {}

    @Override
    public void run() {}

    @Override
    protected void doRegister() {
        super.doRegister();
        this.schedule();
    }

    @Override
    protected void doUnregister() {
        super.doUnregister();
        if (this.runnable != null) this.runnable.cancel();
    }
}