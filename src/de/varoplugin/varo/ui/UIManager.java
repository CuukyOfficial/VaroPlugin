package de.varoplugin.varo.ui;

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

    public UIManager(Plugin plugin) {
        this.plugin = plugin;
        this.listener = Arrays.asList(new LoadingStatePrinter(plugin));
    }

    @Override
    public void registerListener() {
        this.listener.forEach(listener -> this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin));
    }
}