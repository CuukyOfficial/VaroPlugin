package de.varoplugin.varo.ui;

import de.varoplugin.varo.api.event.VaroLoadingStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.ui.commands.TestCommand;
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
    private final Varo varo;
    private final List<Listener> listener;
    private final LoadingStatePrinter loadingStatePrinter;

    public UIManager(Plugin plugin, Varo varo) {
        this.plugin = plugin;
        this.varo = varo;
        this.loadingStatePrinter = new LoadingStatePrinter(plugin);
        this.listener = Arrays.asList(this.loadingStatePrinter);
    }

    @Override
    public void registerUI() {
        plugin.getServer().getPluginCommand("test").setExecutor(new TestCommand(this.varo));
        this.listener.forEach(listener -> this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin));
    }

    @Override
    public void printDisableMessage(VaroLoadingStateChangeEvent event) {
        this.loadingStatePrinter.onLoadingStateUpdate(event);
    }
}