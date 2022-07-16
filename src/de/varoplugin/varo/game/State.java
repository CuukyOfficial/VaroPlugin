package de.varoplugin.varo.game;

public interface State {

    boolean allowsNonRegistered();

    int getPriority();

}