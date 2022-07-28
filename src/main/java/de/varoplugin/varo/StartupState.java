package de.varoplugin.varo;

public enum StartupState implements VaroLoadingState {

    INITIALIZING("INIT", null),
    REGISTERING_TASKS("REGISTERING_TASKS", "Registering tasks..."),
    LOADING_STATS("STATS", "Loading stats..."),
    STARTING_BOTS("BOTS", "Starting bots..."),
    FINISHED("FINISHED", "Finished loading %s");

    private final String name;
    private final String message;

    StartupState(String name, String message) {
        this.name = name;
        this.message = message;
    }

    @Override
    public boolean hasMessage() {
        return this.message != null;
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