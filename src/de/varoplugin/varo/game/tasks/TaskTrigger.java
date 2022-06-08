package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.game.Varo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Trggers tasks.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class TaskTrigger extends VaroListener implements VaroTaskTrigger {

    private final Collection<TaskRegistrable> tasks;
    private final Map<Class<? extends VaroListener>, Supplier<Boolean>> activatedChecks;

    public TaskTrigger(Varo varo, TaskRegistrable... tasks) {
        super(varo);

        this.tasks = new ArrayList<>(Arrays.asList(tasks));
        this.activatedChecks = new HashMap<>();
    }

    protected boolean addCheck(Class<? extends VaroListener> aClass, Supplier<Boolean> check) {
        return this.activatedChecks.put(aClass, check) == null;
    }

    protected boolean isActivated(Class<? extends VaroListener> exclude) {
        return this.activatedChecks.keySet().stream().filter(aClass -> !aClass.equals(exclude))
            .map(this.activatedChecks::get).allMatch(Supplier::get);
    }

    @Override
    public boolean addTask(TaskRegistrable task) {
        return this.tasks.add(task);
    }

    @Override
    public boolean registerTasks() {
        return this.tasks.stream().allMatch(TaskRegistrable::register);
    }

    @Override
    public boolean unregisterTasks() {
        return this.tasks.stream().allMatch(TaskRegistrable::unregister);
    }

    @Override
    public boolean register() {
        if (super.register()) {
            if (this.isActivated(null)) this.registerTasks();
            return true;
        }
        return false;
    }

    @Override
    public boolean unregister() {
        this.unregisterTasks();
        return super.unregister();
    }
}