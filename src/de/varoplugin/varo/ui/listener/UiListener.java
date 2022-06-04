package de.varoplugin.varo.ui.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
abstract class UiListener implements Listener {

    private final Plugin plugin;

    protected UiListener(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public Logger getLogger() {
        return this.plugin.getLogger();
    }
}