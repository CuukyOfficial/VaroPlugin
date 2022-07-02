package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.data.VaroDataGame;
import de.varoplugin.varo.game.entity.player.VaroPlayerContainer;

public interface Varo extends VaroPlayerContainer, VaroDataGame {

    void initialize(VaroPlugin plugin);

    VaroState getState();

    boolean setState(VaroState state);

    VaroPlugin getPlugin();

}