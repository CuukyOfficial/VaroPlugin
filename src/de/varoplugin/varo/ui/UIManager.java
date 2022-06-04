package de.varoplugin.varo.ui;

import de.varoplugin.varo.api.event.VaroLoadingStateChangeEvent;
import de.varoplugin.varo.ui.listener.LoadingStatePrinter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class UIManager implements VaroUIManager {

    private final Plugin plugin;
    private final List<Listener> listener;
    private final LoadingStatePrinter loadingStatePrinter;

    public UIManager(Plugin plugin) {
        this.plugin = plugin;
        this.loadingStatePrinter = new LoadingStatePrinter(plugin);
        this.listener = Arrays.asList(this.loadingStatePrinter);
    }

    @Override
    public void registerListener() {
        this.listener.forEach(listener -> this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin));
    }

    @Override
    public void printDisableMessage(VaroLoadingStateChangeEvent event) {
        this.loadingStatePrinter.onLoadingStateUpdate(event);
    }
}