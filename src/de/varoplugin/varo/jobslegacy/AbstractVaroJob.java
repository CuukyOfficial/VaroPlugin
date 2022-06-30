package de.varoplugin.varo.jobslegacy;

import de.varoplugin.varo.game.Varo;

abstract class AbstractVaroJob implements VaroJob {

    private boolean registered;
    private Varo varo;

    protected void checkInitialization() {
        if (!this.isInitialized()) throw new IllegalStateException("Task not initialized");
    }

    protected abstract void doRegister();

    protected abstract void doUnregister();

    @Override
    public boolean isInitialized() {
        return this.varo != null;
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public boolean unregister() {
        if (!this.registered) return false;
        this.doUnregister();
        return (this.registered = false);
    }

    @Override
    public boolean register(Varo varo) {
        if (this.registered) return false;
        this.varo = varo;
        this.doRegister();
        return (this.registered = true);
    }

    @Override
    public Varo getVaro() {
        return this.varo;
    }
}
