package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.ui.UiElement;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
abstract class UiListener implements UiElement, Listener {

    private Plugin plugin;

    @Override
    public void register(VaroPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public Logger getLogger() {
        return this.plugin.getLogger();
    }
}