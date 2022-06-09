package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTriggerHolder;
import de.varoplugin.varo.tasks.game.LobbyLoginTask;
import de.varoplugin.varo.tasks.game.RunningLoginTask;
import de.varoplugin.varo.tasks.game.VaroStateTrigger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultGameTrigger implements VaroTriggerHolder<VaroRegisterInfo> {

    LOBBY_TRIGGER(() -> new VaroStateTrigger<>(GameState.LOBBY, new LobbyLoginTask())),
    RUNNING_TRIGGER(() -> new VaroStateTrigger<>(GameState.RUNNING, new RunningLoginTask())),
    // TODO: Decide whether only the default or all triggers should be added to mass recording
    MASS_RECORDING_TRIGGER(RUNNING_TRIGGER.trigger),
    FINISHED_TRIGGER(() -> new VaroStateTrigger<>(GameState.FINISHED));

    private final DefaultGameTrigger parent;
    private final Supplier<VaroTaskTrigger<VaroRegisterInfo>> trigger;
    private final List<Supplier<VaroTask<VaroRegisterInfo>>> tasks;

    @SafeVarargs
    DefaultGameTrigger(DefaultGameTrigger parent, Supplier<VaroTaskTrigger<VaroRegisterInfo>> trigger, Supplier<VaroTask<VaroRegisterInfo>>... tasks) {
        this.parent = parent;
        this.trigger = trigger;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    @SafeVarargs
    DefaultGameTrigger(Supplier<VaroTaskTrigger<VaroRegisterInfo>> trigger, Supplier<VaroTask<VaroRegisterInfo>>... tasks) {
        this(null, trigger, tasks);
    }

    @Override
    public VaroTaskTrigger<VaroRegisterInfo> createTrigger() {
        VaroTaskTrigger<VaroRegisterInfo> trigger = this.trigger.get();
        this.tasks.stream().map(Supplier::get).forEach(trigger::addTask);
        if (this.parent != null) {
            VaroTaskTrigger<VaroRegisterInfo> pTrigger = this.parent.createTrigger();
            pTrigger.addTask(trigger);
            trigger = pTrigger;
        }
        return trigger;
    }

    @Override
    public void addTask(Supplier<VaroTask<VaroRegisterInfo>> taskSupplier) {
        this.tasks.add(taskSupplier);
    }
}