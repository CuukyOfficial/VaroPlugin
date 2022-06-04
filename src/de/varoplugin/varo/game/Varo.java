package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface Varo {

    void initialize(VaroPlugin plugin);

    boolean register(VaroPlayer player);

    VaroState getState();

    boolean setState(VaroState state);

    VaroPlugin getPlugin();

}