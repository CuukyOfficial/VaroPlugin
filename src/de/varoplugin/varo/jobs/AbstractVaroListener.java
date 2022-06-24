package de.varoplugin.varo.jobs;

import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;

/**
 * Represents any Varo listener.
 * Unregisters on plugin disable.
 */
public abstract class AbstractVaroListener extends AbstractVaroJob implements Listener {

    protected void doRegister() {
        this.getVaro().getPlugin().getServer().getPluginManager().registerEvents(this, this.getVaro().getPlugin());
    }

    protected void doUnregister() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(this.getVaro().getPlugin())) return;
        this.unregister();
    }
}