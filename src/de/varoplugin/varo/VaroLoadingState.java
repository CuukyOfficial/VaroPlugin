package de.varoplugin.varo;

/**
 * Represents any loading state the plugin can be in while loading.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroLoadingState {

    boolean hasMessage();

    String getName();

    String formatMessage(Object... args);

}