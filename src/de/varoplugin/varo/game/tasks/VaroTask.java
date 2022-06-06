package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents any Varo task.
 * Registers itself as a bukkit listener and calls "schedule" on registration.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroTask extends BukkitRunnable implements CancelableTask {

    private boolean registered;
    protected final Varo varo;

    public VaroTask(Varo varo) {
        this.varo = varo;
    }

    /**
     * Override this to schedule the task
     */
    protected void schedule() {
    }

    @Override
    public void run() {
    }

    private void doRegister() {
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(this, this.varo.getPlugin());
        this.schedule();
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public boolean register() {
        if (this.registered) return false;
        this.doRegister();
        this.registered = true;
        return true;
    }

    @Override
    public boolean unregister() {
        if (!this.registered) return false;
        try {
            this.cancel();
        } catch (IllegalStateException ignored) {}
        return CancelableTask.super.unregister();
    }
}