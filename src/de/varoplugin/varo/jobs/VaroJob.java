package de.varoplugin.varo.jobs;

import de.varoplugin.varo.game.Varo;

/**
 * Represents tasks that unregister automatically after being registered.
 */
public interface VaroJob {

    /**
     * If the task has been initialized.
     *
     * @return If the task has been initialized
     */
    boolean isInitialized();

    /**
     * Returns if the task is registered currently.
     *
     * @return If the task is registered
     */
    boolean isRegistered();

    /**
     * Registers the task. (Listeners and scheduler)
     * Does not register twice if called twice.
     *
     * @return If the task has been registered
     */
    boolean register(Varo varo);

    /**
     * Unregisters the task.
     * The task will not register itself.
     *
     * @return If the task has been unregistered
     */
    boolean unregister();

    Varo getVaro();

}