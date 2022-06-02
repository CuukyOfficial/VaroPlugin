package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.event.VaroLoadingStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class StartupPrinter extends UiListener {

    private static final String FORMAT = "[STARTUP] %s";

    public StartupPrinter(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onLoadingStateUpdate(VaroLoadingStateChangeEvent event) {
        this.getLogger().log(Level.INFO, String.format(FORMAT, event.getMessage()));
    }
}