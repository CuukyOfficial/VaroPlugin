package de.varoplugin.varo.tasks;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Listener;

/**
 * Represents tasks that unregister automatically after being registered.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTask extends Listener {

    /**
     * Returns if the task is registered currently.
     *
     * @return If the task is registered.
     */
    boolean isRegistered();

    /**
     * Registers the task. (Listeners and scheduler)
     * Does not register twice if called twice.
     *
     * @return If the task has been registered.
     */
    boolean register(Varo varo);

    /**
     * Unregisters the task.
     * The task will not register itself.
     *
     * @return If the task has been unregistered.
     */
    boolean unregister();

}