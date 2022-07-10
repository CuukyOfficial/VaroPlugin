package de.varoplugin.varo.game;

public interface VaroBuilder {

    VaroBuilder state(State state);

    Varo create();
}
