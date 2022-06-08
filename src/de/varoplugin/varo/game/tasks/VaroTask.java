package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents any Varo task.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroTask extends VaroListener implements TaskRegistrable, Runnable {

    private BukkitRunnable runnable;

    public VaroTask(Varo varo) {
        super(varo);
    }

    protected BukkitRunnable createRunnable() {
        return this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                VaroTask.this.run();
            }
        };
    }

    /**
     * Override this to schedule the task
     */
    protected void schedule() {
    }

    @Override
    public void run() {
    }


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