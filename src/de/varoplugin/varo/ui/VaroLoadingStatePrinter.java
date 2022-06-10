package de.varoplugin.varo.ui;

import de.varoplugin.varo.VaroLoadingState;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroLoadingStatePrinter extends UiElement {

    void onLoadingStateUpdate(VaroLoadingState state, Object... format);

}