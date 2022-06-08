package de.varoplugin.varo.game.tasks;

import java.util.ArrayList;
import java.util.Arrays;
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
public abstract class VaroTaskTrigger extends VaroListener implements TaskTrigger {

    private final Collection<VaroTask> tasks;
    private final Map<Class<? extends VaroListener>, Supplier<Boolean>> activatedChecks;

    public VaroTaskTrigger(VaroTask... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
        this.activatedChecks = new HashMap<>();
    }

    protected boolean addCheck(Class<? extends VaroListener> aClass, Supplier<Boolean> check) {
        return this.activatedChecks.put(aClass, check) == null;
    }

    protected boolean isActivated(Class<? extends VaroListener> exclude) {
        this.checkInitialization();
        return this.activatedChecks.keySet().stream().filter(aClass -> !aClass.equals(exclude))
            .map(this.activatedChecks::get).allMatch(Supplier::get);
    }

    @Override
    protected void doRegister() {
        if (this.isActivated(VaroTaskTrigger.class)) this.registerTasks();
        super.doRegister();
    }

    @Override
    protected void doUnregister() {
        this.unregisterTasks();
        super.doUnregister();
    }

    @Override
    public boolean addTask(VaroTask task) {
        boolean add = this.tasks.add(task);
        if (this.isRegistered()) task.register(this.varo);
        return add;
    }

    @Override
    public void registerTasks() {
        this.checkInitialization();
        this.tasks.forEach(t -> t.register(this.varo));
    }

    @Override
    public void unregisterTasks() {
        this.checkInitialization();
        this.tasks.forEach(VaroTask::unregister);
    }
}