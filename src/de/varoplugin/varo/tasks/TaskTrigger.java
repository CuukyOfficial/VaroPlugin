package de.varoplugin.varo.tasks;

/**
 * A task trigger contains tasks and registers them if desired.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface TaskTrigger extends VaroTask {

    /**
     * Adds a task to this trigger.
     * If this trigger is currently active it will register the task.
     *
     * @param task The task
     * @return Returns if the task has been added
     */
    boolean addTask(VaroTask task);

    /**
     * Registers all tasks of this trigger.
     */
    void registerTasks();

    /**
     * Unregisters all tasks of this trigger.
     */
    void unregisterTasks();

}