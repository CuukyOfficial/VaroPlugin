package de.varoplugin.varo.game.strike;

public enum VaroStrikeType implements StrikeType {

    POST_COORDINATES("post_coordinates"),
    CLEAR_INVENTORY("clear_inventory"),
    KILL("kill");

    private final String id;

    VaroStrikeType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}