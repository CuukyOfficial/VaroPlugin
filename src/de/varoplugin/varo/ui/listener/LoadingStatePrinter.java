package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.StartupState;
import de.varoplugin.varo.VaroLoadingState;
import de.varoplugin.varo.ui.VaroLoadingStatePrinter;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class LoadingStatePrinter extends UiListener implements VaroLoadingStatePrinter {

    private static final String[] BANNER = {" _     _                  ______  _              _       ", 
        "(_)   (_)                (_____ \\| |            (_)      ", 
        " _     _ _____  ____ ___  _____) ) | _   _  ____ _ ____  ", 
        "| |   | (____ |/ ___) _ \\|  ____/| || | | |/ _  | |  _ \\ ", 
        " \\ \\ / // ___ | |  | |_| | |     | || |_| ( (_| | | | | |", 
        "  \\___/ \\_____|_|   \\___/|_|      \\_)____/ \\___ |_|_| |_|", 
        "                                          (_____|        "};

    private static final String FORMAT = "%s";

    private void printBanner() {
        Arrays.stream(BANNER).forEach(line -> this.getLogger().log(Level.INFO, line));
    }

    public void onLoadingStateUpdate(VaroLoadingState state, Object... format) {
        if (state.equals(StartupState.values()[0])) this.printBanner();
        this.getLogger().log(Level.INFO, String.format(FORMAT, state.formatMessage(format)));
    }
}