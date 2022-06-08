package de.varoplugin.varo.game.tasks;

import org.bukkit.event.Listener;

/**
 * Represents tasks that unregister automatically after being registered.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface TaskRegistrable extends Listener {

    /**
     * Does not register twice if called twice.
     */
    boolean register();

    boolean unregister();

}