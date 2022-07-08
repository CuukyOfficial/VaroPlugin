package de.varoplugin.varo.api.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractExecutable extends AbstractTask implements VaroTask, Runnable {

    private BukkitRunnable runnable;

    public AbstractExecutable(Varo varo) {
       super(varo);
    }

    private BukkitRunnable createRunnable() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                AbstractExecutable.this.run();
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
