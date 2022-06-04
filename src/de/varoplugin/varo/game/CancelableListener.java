package de.varoplugin.varo.game;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface CancelableListener extends Listener {

    boolean shallListen();

    default void unregister() {
        HandlerList.unregisterAll(this);
    }
}