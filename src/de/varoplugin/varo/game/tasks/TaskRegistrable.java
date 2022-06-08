package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Listener;

/**
 * Represents tasks that unregister automatically after being registered.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface TaskRegistrable extends Listener {

    boolean isRegistered();

    /**
     * Does not register twice if called twice.
     */
    boolean register(Varo varo);

    boolean unregister();

}