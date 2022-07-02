package de.varoplugin.varo;

/**
 * Represents any loading state the plugin can be in while loading.
 */
public interface VaroLoadingState {

    boolean hasMessage();

    String getName();

    String formatMessage(Object... args);

}