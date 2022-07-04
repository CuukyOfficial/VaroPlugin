package de.varoplugin.varo.api.task;

import de.varoplugin.varo.game.Varo;

public abstract class AbstractTask implements VaroTask {

    private Varo varo;
    private boolean registered;

    public AbstractTask(Varo varo) {
        this.varo = varo;
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    @Override
    public final void enable() {
        this.registered = true;
        this.onEnable();
    }

    @Override
    public final void disable() {
        this.registered = false;
        this.onDisable();
    }

    @Override
    public boolean isEnabled() {
        return this.registered;
    }

    @Override
    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public VaroTask clone() {
        try {
            AbstractTask task = (AbstractTask) super.clone();
            task.varo = this.varo;
            return task;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
