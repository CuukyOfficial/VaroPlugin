package de.varoplugin.varo.task;

import de.varoplugin.varo.game.Varo;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AbstractTask implements VaroRegistrable, Runnable {

    private BukkitRunnable runnable;
    private Varo varo;

    public AbstractTask(Varo varo) {
        this.varo = varo;
    }

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

    @Override
    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public VaroRegistrable clone() {
        try {
            AbstractTask task = (AbstractTask) super.clone();
            task.varo = this.varo;
            return task;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
