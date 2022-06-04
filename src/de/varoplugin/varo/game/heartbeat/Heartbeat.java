package de.varoplugin.varo.game.heartbeat;

import de.varoplugin.varo.game.Varo;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface Heartbeat extends Runnable {

    void initialize(Varo varo);

    void cancel();

}