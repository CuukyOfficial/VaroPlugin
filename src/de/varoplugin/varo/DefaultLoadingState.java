package de.varoplugin.varo;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultLoadingState implements VaroLoadingState {

    INITIALIZING("INIT", "Booting up %s v%s..."),
    LOADING_STATS("STATS", "Loading stats..."),
    FINISHED("FINISHED", "Finished loading %s");

    private final String name;
    private final String message;

    DefaultLoadingState(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }
}