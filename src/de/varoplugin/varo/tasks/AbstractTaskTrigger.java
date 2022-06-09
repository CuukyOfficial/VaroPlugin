package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Triggers tasks.
 * Destroys itself.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractTaskTrigger<I extends VaroRegisterInfo> extends AbstractVaroListener<I>
    implements VaroTaskTrigger<I> {

    private final Collection<VaroTask<I>> tasks;
    private boolean tasksEnabled;

    @SafeVarargs
    public AbstractTaskTrigger(VaroTask<I>... children) {
        this.tasks = new ArrayList<>(Arrays.asList(children));
    }

    @Override
    public void addTasksTo(VaroTaskTrigger<I> copyInto) {
        this.tasks.forEach(copyInto::addTask);
    }

    @Override
    public void addTask(VaroTask<I> task) {
        this.tasks.add(task);
        if (this.tasksEnabled && this.shouldEnable()) task.register(this.getInfo());
    }

    @Override
    protected void doRegister() {
        if (this.shouldEnable()) this.registerTasks();
        super.doRegister();
    }

    @Override
    protected void doUnregister() {
        this.unregisterTasks();
        super.doUnregister();
    }

    @Override
    public void registerTasks() {
        if (this.tasksEnabled) return;
        this.checkInitialization();
        this.tasks.forEach(t -> t.register(this.getInfo()));
        this.tasksEnabled = true;
    }

    @Override
    public void unregisterTasks() {
        this.checkInitialization();
        this.tasks.forEach(VaroTask::unregister);
        this.tasksEnabled = false;
    }
}