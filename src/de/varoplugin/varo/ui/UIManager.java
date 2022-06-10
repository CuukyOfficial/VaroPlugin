package de.varoplugin.varo.ui;

import de.varoplugin.varo.VaroLoadingState;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.ui.commands.TestCommand;
import de.varoplugin.varo.ui.listener.LoadingStatePrinter;
import de.varoplugin.varo.ui.listener.PlayerKickListener;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class UIManager implements VaroUIManager {

    private final Collection<UiElement> elements;
    private final VaroLoadingStatePrinter loadingStatePrinter;

    public UIManager() {
        this.loadingStatePrinter = new LoadingStatePrinter();
        this.elements = Arrays.asList(this.loadingStatePrinter, new PlayerKickListener(), new TestCommand());
    }

    @Override
    public void register(VaroPlugin plugin) {
        this.elements.forEach(element -> element.register(plugin));
    }

    @Override
    public void onLoadingStateUpdate(VaroLoadingState state, Object... format) {
        this.loadingStatePrinter.onLoadingStateUpdate(state, format);
    }
}