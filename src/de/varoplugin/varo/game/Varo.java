package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.game.entity.player.VaroPlayerContainer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface Varo extends VaroPlayerContainer {

    void initialize(VaroPlugin plugin);

    VaroState getState();

    boolean setState(VaroState state);

    VaroPlugin getPlugin();

}