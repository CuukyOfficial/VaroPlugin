package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents any Varo task.
 */
public abstract class AbstractVaroTask<I extends VaroRegisterInfo> extends AbstractVaroListener<I> implements VaroTask<I>, Runnable {

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