package de.varoplugin.varo.jobslegacy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Triggers tasks.
 * Destroys itself.
 */
public abstract class AbstractTaskTrigger extends AbstractVaroListener implements VaroTrigger {

    private final Collection<VaroJob> tasks;
    private boolean tasksEnabled;

    public AbstractTaskTrigger(VaroJob... children) {
        this.tasks = new ArrayList<>(Arrays.asList(children));
    }

    @Override
    public void addJobsTo(VaroTrigger copyInto) {
        this.tasks.forEach(copyInto::addJob);
    }

    @Override
    public void addJob(VaroJob task) {
        this.tasks.add(task);
        if (this.tasksEnabled && this.shouldEnable()) task.register(this.getVaro());
    }

    @Override
    protected void doRegister() {
        if (this.shouldEnable()) this.registerJobs();
    }

    @Override
    protected void doUnregister() {
        this.unregisterJobs();
    }

    @Override
    public void registerJobs() {
        if (this.tasksEnabled) return;
        this.checkInitialization();
        this.tasks.forEach(t -> t.register(this.getVaro()));
        this.tasksEnabled = true;
    }

    @Override
    public void unregisterJobs() {
        this.checkInitialization();
        this.tasks.forEach(VaroJob::unregister);
        this.tasksEnabled = false;
    }
}