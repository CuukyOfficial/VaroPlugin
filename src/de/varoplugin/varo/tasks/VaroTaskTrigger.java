package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;

import java.util.Collection;

/**
 * A task trigger contains tasks and registers them if desired.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTaskTrigger<I extends VaroRegisterInfo> extends VaroTask<I>, Cloneable {

    boolean shouldEnable();

    /**
     * Adds a task to this trigger.
     * If this trigger is currently active it will register the task.
     *
     * @param task The task
     */
    void addTask(VaroTask<I> task);

    Collection<VaroTask<I>> getTasks();

    /**
     * Registers all tasks of this trigger.
     */
    void registerTasks();

    /**
     * Unregisters all tasks of this trigger.
     */
    void unregisterTasks();

}