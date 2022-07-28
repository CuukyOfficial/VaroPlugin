package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.StartupState;
import de.varoplugin.varo.VaroLoadingState;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.ui.VaroLoadingStatePrinter;
import de.varoplugin.varo.util.ZipFileUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

public class LoadingStatePrinter extends UiListener implements VaroLoadingStatePrinter {

    private static final String BANNER_LOCATION = "banner.txt";
    private static final String FORMAT = "%s";

    private String[] banner;

    @Override
    public void register(VaroPlugin plugin) {
        super.register(plugin);
        try {
            this.banner = Objects.requireNonNull(ZipFileUtils.readFileFromZip(plugin.getFile(), BANNER_LOCATION)).split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printBanner() {
        Arrays.stream(this.banner).forEach(line -> this.getLogger().log(Level.INFO, line));
    }

    public void onLoadingStateUpdate(VaroLoadingState state, Object... format) {
        if (state.equals(StartupState.values()[0])) this.printBanner();
        if (!state.hasMessage()) return;
        this.getLogger().log(Level.INFO, String.format(FORMAT, state.formatMessage(format)));
    }
}