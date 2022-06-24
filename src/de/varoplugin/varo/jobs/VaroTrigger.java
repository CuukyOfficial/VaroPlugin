package de.varoplugin.varo.jobs;

/**
 * A task trigger contains tasks and registers them if desired.
 */
public interface VaroTrigger extends VaroJob, Cloneable {

    boolean shouldEnable();

    void addJobsTo(VaroTrigger copyInto);

    /**
     * Adds a task to this trigger.
     * If this trigger is currently active it will register the task.
     *
     * @param task The task
     */
    void addJob(VaroJob task);

    /**
     * Registers all tasks of this trigger.
     */
    void registerJobs();

    /**
     * Unregisters all tasks of this trigger.
     */
    void unregisterJobs();

}