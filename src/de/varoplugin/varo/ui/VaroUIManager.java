package de.varoplugin.varo.ui;

import de.varoplugin.varo.api.event.VaroLoadingStateChangeEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroUIManager {

    void registerListener();

    void printDisableMessage(VaroLoadingStateChangeEvent event);

}