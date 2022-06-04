package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.StartupState;
import de.varoplugin.varo.api.event.VaroLoadingStateChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class LoadingStatePrinter extends UiListener {

    private static final String BANNER = " _     _                  ______  _              _       \n" +
        "(_)   (_)                (_____ \\| |            (_)      \n" +
        " _     _ _____  ____ ___  _____) ) | _   _  ____ _ ____  \n" +
        "| |   | (____ |/ ___) _ \\|  ____/| || | | |/ _  | |  _ \\ \n" +
        " \\ \\ / // ___ | |  | |_| | |     | || |_| ( (_| | | | | |\n" +
        "  \\___/ \\_____|_|   \\___/|_|      \\_)____/ \\___ |_|_| |_|\n" +
        "                                          (_____|        ";

    private static final String FORMAT = "%s";

    public LoadingStatePrinter(Plugin plugin) {
        super(plugin);
    }

    private void printBanner() {
        Arrays.stream(BANNER.split("\\n")).forEach(line -> this.getLogger().log(Level.INFO, line));
    }

    @EventHandler
    public void onLoadingStateUpdate(VaroLoadingStateChangeEvent event) {
        if (event.getState().equals(StartupState.values()[0])) this.printBanner();
        this.getLogger().log(Level.INFO, String.format(FORMAT, event.getMessage()));
    }
}