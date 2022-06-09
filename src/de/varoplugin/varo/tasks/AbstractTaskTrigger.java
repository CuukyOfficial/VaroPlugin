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
public abstract class AbstractTaskTrigger<I extends VaroRegisterInfo> extends AbstractVaroListener<I> implements VaroTaskTrigger<I> {

    private final Collection<VaroTask<I>> tasks;

    @SafeVarargs
    public AbstractTaskTrigger(VaroTask<I>... children) {
        this.tasks = new ArrayList<>();
        this.tasks.addAll(Arrays.asList(children));
    }

    @Override
    public boolean addTask(VaroTask<I> task) {
        boolean add = this.tasks.add(task);
        if (this.isRegistered() && this.shouldEnable()) task.register(this.getInfo());
        return add;
    }

    @Override
    protected void doRegister() {
        this.registerTasks();
        super.doRegister();
    }

    @Override
    protected void doUnregister() {
        this.unregisterTasks();
        super.doUnregister();
    }

    @Override
    public void registerTasks() {
        this.checkInitialization();
        if (!this.shouldEnable()) return;
        this.tasks.forEach(t -> t.register(this.getInfo()));
    }

    @Override
    public void unregisterTasks() {
        this.checkInitialization();
        this.tasks.forEach(VaroTask::unregister);
    }
}