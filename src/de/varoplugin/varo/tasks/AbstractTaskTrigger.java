package de.varoplugin.varo.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Triggers tasks.
 * Destroys itself.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class AbstractTaskTrigger<T extends VaroTask> extends AbstractVaroListener implements VaroTaskTrigger<T> {

    private final Collection<T> tasks;
    private final Map<Class<? extends AbstractVaroListener>, Supplier<Boolean>> activatedChecks;

    public AbstractTaskTrigger() {
        this.tasks = new ArrayList<>();
        this.activatedChecks = new HashMap<>();
    }

    protected boolean addCheck(Class<? extends AbstractVaroListener> aClass, Supplier<Boolean> check) {
        return this.activatedChecks.put(aClass, check) == null;
    }

    protected boolean isActivated(Class<? extends AbstractVaroListener> exclude) {
        this.checkInitialization();
        return this.activatedChecks.keySet().stream().filter(aClass -> !aClass.equals(exclude))
            .map(this.activatedChecks::get).allMatch(Supplier::get);
    }

    protected void register(T task) {
        task.register(this.varo);
    }

    @Override
    protected void doRegister() {
        if (this.isActivated(AbstractTaskTrigger.class)) this.registerTasks();
        super.doRegister();
    }

    @Override
    protected void doUnregister() {
        this.unregisterTasks();
        super.doUnregister();
    }

    @Override
    public boolean addTask(T task) {
        boolean add = this.tasks.add(task);
        if (this.isRegistered()) this.register(task);
        return add;
    }

    @Override
    public void registerTasks() {
        this.checkInitialization();
        this.tasks.forEach(this::register);
    }

    @Override
    public void unregisterTasks() {
        this.checkInitialization();
        this.tasks.forEach(VaroTask::unregister);
    }
}