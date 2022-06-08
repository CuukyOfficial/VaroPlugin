package de.varoplugin.varo.tasks;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.HandlerList;

/**
 * Represents any Varo listener.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractVaroListener implements VaroTask {

    private boolean registered;
    protected Varo varo;

    protected void checkInitialization() {
        if (!this.isInitialized()) throw new IllegalStateException("Task not initialized");
    }

    protected boolean isInitialized() {
        return this.varo != null;
    }

    protected void doRegister() {
        this.checkInitialization();
        this.varo.getPlugin().getServer().getPluginManager().registerEvents(this, this.varo.getPlugin());
    }

    protected void doUnregister() {
        this.checkInitialization();
        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public boolean register(Varo varo) {
        if (this.registered) return false;
        this.varo = varo;
        this.doRegister();
        return (this.registered = true);
    }

    @Override
    public boolean unregister() {
        if (!this.registered) return false;
        this.doUnregister();
        return (this.registered = false);
    }
}