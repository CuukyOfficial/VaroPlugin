package de.varoplugin.varo.ui;

import de.varoplugin.varo.VaroLoadingState;

public interface VaroLoadingStatePrinter extends UiElement {

    void onLoadingStateUpdate(VaroLoadingState state, Object... format);

}