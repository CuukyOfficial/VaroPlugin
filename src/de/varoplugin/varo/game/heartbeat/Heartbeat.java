package de.varoplugin.varo.game.heartbeat;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Listener;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface Heartbeat extends Runnable, Listener {

    void initialize(Varo varo);

    void cancel();

}