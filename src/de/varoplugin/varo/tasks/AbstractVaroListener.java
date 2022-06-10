package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.server.PluginDisableEvent;

/**
 * Represents any Varo listener.
 * Unregisters on plugin disable.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractVaroListener<T extends VaroRegisterInfo> implements VaroTask<T> {

    private boolean registered;
    private T info;

    protected void checkInitialization() {
        if (!this.isInitialized()) throw new IllegalStateException("Task not initialized");
    }

    protected void doRegister() {
        this.checkInitialization();
        this.info.getVaro().getPlugin().getServer().getPluginManager().registerEvents(this, this.info.getVaro().getPlugin());
    }

    protected void doUnregister() {
        this.checkInitialization();
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(this.info.getVaro().getPlugin())) return;
        this.unregister();
    }

    @Override
    public boolean isInitialized() {
        return this.info != null;
    }

    @Override
    public boolean isRegistered() {
        return this.registered;
    }

    @Override
    public boolean register(T info) {
        if (this.registered) return false;
        this.info = info;
        this.doRegister();
        return (this.registered = true);
    }

    @Override
    public boolean unregister() {
        if (!this.registered) return false;
        this.doUnregister();
        return (this.registered = false);
    }

    @Override
    public T getInfo() {
        return this.info;
    }
}