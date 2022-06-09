package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.tasks.VaroTriggerHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultPlayerTrigger implements VaroTriggerHolder<VaroPlayerTrigger, VaroPlayerTask> {

    LOBBY_PLAYER(() -> new PlayerTrigger(GameState.LOBBY), NoMoveTask::new);

    private final Supplier<VaroPlayerTrigger> trigger;
    private final List<Supplier<VaroPlayerTask>> tasks;

    @SafeVarargs
    DefaultPlayerTrigger(Supplier<VaroPlayerTrigger> trigger, Supplier<VaroPlayerTask>... tasks) {
        this.trigger = trigger;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    @SafeVarargs
    DefaultPlayerTrigger(Supplier<VaroPlayerTrigger> trigger, DefaultPlayerTrigger parent, Supplier<VaroPlayerTask>... tasks) {
        this(trigger, tasks);

        this.tasks.addAll(parent.tasks);
    }

    @Override
    public VaroPlayerTrigger createTrigger() {
        VaroPlayerTrigger trigger = this.trigger.get();
        this.tasks.stream().map(Supplier::get).forEach(trigger::addTask);
        return trigger;
    }

    @Override
    public void addTask(Supplier<VaroPlayerTask> taskSupplier) {
        this.tasks.add(taskSupplier);
    }
}