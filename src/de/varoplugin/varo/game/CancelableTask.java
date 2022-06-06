package de.varoplugin.varo.game;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Represents tasks that unregister automatically after being registered.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface CancelableTask extends Listener {

    boolean isRegistered();

    /**
     * Does not register twice if called twice.
     */
    boolean register();

    default boolean unregister() {
        HandlerList.unregisterAll(this);
        return true;
    }
}