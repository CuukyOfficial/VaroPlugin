package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.tasks.VaroTriggerHolder;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTask;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultGameTrigger implements Listener, VaroTriggerHolder {

    LOBBY_TRIGGER(() -> new VaroStateTrigger(GameState.LOBBY), LobbyLoginTask::new),
    RUNNING_TRIGGER(() -> new VaroStateTrigger(GameState.RUNNING), RunningLoginTask::new),
    // TODO: Decide whether only the default or all triggers should be added to mass recording
    MASS_RECORDING_TRIGGER(() -> new VaroStateTrigger(GameState.MASS_RECORDING), RUNNING_TRIGGER),
    FINISHED_TRIGGER(() -> new VaroStateTrigger(GameState.FINISHED));

    private final Supplier<VaroTaskTrigger> trigger;
    private final List<Supplier<VaroTask>> tasks;

    @SafeVarargs
    DefaultGameTrigger(Supplier<VaroTaskTrigger> trigger, Supplier<VaroTask>... tasks) {
        this.trigger = trigger;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    @SafeVarargs
    DefaultGameTrigger(Supplier<VaroTaskTrigger> trigger, DefaultGameTrigger parent, Supplier<VaroTask>... tasks) {
        this(trigger, tasks);

        this.tasks.addAll(parent.tasks);
    }

    @Override
    public VaroTaskTrigger createTrigger() {
        VaroTaskTrigger trigger = this.trigger.get();
        this.tasks.stream().map(Supplier::get).forEach(trigger::addTask);
        return trigger;
    }

    @Override
    public void addTask(Supplier<VaroTask> taskSupplier) {
        this.tasks.add(taskSupplier);
    }
}