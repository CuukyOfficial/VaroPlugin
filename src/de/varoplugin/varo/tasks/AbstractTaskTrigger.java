package de.varoplugin.varo.tasks;

import de.varoplugin.varo.tasks.register.VaroRegisterInfo;

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
public abstract class AbstractTaskTrigger<T extends VaroRegisterInfo> extends AbstractVaroListener<T> implements VaroTaskTrigger<T> {

    private final Collection<VaroTask<T>> tasks;
    private final Map<VaroTriggerCheckType, Supplier<Boolean>> activatedChecks;
    private final Collection<VaroTaskTrigger<T>> merged;

    public AbstractTaskTrigger(VaroTaskTrigger<T>... combine) {
        this.tasks = new ArrayList<>();
        this.activatedChecks = new HashMap<>();
        this.merged = new ArrayList<>();

        Arrays.stream(combine).forEach(this::hook);
    }

    protected boolean addCheck(VaroTriggerCheckType type, Supplier<Boolean> check) {
        return this.activatedChecks.put(type, check) == null;
    }

    protected void registerTasksActivated(VaroTriggerCheckType exclude) {
        if (!this.isActivated(exclude)) return;
        this.registerTasks();
    }

    @Override
    public void hook(VaroTaskTrigger<T> combine) {
        this.merged.add(combine);
    }

    @Override
    public boolean isActivated(VaroTriggerCheckType exclude) {
        this.checkInitialization();
        boolean merged = this.merged.stream().allMatch(c -> c.isActivated(exclude));
        return merged && this.activatedChecks.keySet().stream().filter(check -> !check.equals(exclude))
            .map(this.activatedChecks::get).allMatch(Supplier::get);
    }

    @Override
    protected void doRegister() {
        if (this.isActivated(null)) this.registerTasks();
        super.doRegister();
    }

    @Override
    protected void doUnregister() {
        this.unregisterTasks();
        super.doUnregister();
    }

    @Override
    public boolean register(T info) {
        this.merged.forEach(merged -> merged.register(info));
        return super.register(info);
    }

    @Override
    public boolean unregister() {
        this.merged.forEach(VaroTask::unregister);
        return super.unregister();
    }

    @Override
    public boolean addTask(VaroTask<T> task) {
        boolean add = this.tasks.add(task);
        if (this.isRegistered() && this.isActivated(null)) task.register(this.getInfo());
        return add;
    }

    @Override
    public void registerTasks() {
        this.checkInitialization();
        this.merged.forEach(VaroTaskTrigger::registerTasks);
        this.tasks.forEach(t -> t.register(this.getInfo()));
    }

    @Override
    public void unregisterTasks() {
        this.checkInitialization();
        this.merged.forEach(VaroTaskTrigger::unregisterTasks);
        this.tasks.forEach(VaroTask::unregister);
    }
}