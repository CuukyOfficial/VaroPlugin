package de.varoplugin.varo.game.strike;

public enum GameStrikeType implements VaroStrikeType {

    POST_COORDINATES("post_coordinates"),
    CLEAR_INVENTORY("clear_inventory"),
    KILL("kill");

    private final String id;

    GameStrikeType(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}