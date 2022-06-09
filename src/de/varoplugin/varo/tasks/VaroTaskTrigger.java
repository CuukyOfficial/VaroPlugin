package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;

/**
 * A task trigger contains tasks and registers them if desired.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroTaskTrigger<I extends VaroRegisterInfo> extends VaroTask<I>, Cloneable {

    boolean isActivated(VaroTriggerCheckType exclude);

    /**
     * Merges the given trigger into this one.
     *
     * @param combine The trigger that will be merged into this
     */
     void hook(VaroTaskTrigger<I> combine);

    /**
     * Adds a task to this trigger.
     * If this trigger is currently active it will register the task.
     *
     * @param task The task
     * @return Returns if the task has been added
     */
    boolean addTask(VaroTask<I> task);

    /**
     * Registers all tasks of this trigger.
     */
    void registerTasks();

    /**
     * Unregisters all tasks of this trigger.
     */
    void unregisterTasks();

}