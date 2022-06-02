package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;

public interface Varo {

    void initialize(VaroPlugin plugin);

    VaroState getState();

    boolean setState(VaroState state);

    VaroPlugin getPlugin();

}