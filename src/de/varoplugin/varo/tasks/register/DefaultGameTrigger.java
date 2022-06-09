package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTriggerHolder;
import de.varoplugin.varo.tasks.game.LobbyLoginTask;
import de.varoplugin.varo.tasks.game.RunningLoginTask;
import de.varoplugin.varo.tasks.game.VaroStateTrigger;

import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultGameTrigger implements VaroTriggerHolder<VaroRegisterInfo> {

    LOBBY_TRIGGER(() -> new VaroStateTrigger<>(GameState.LOBBY, new LobbyLoginTask())),
    STARTING_TRIGGER(() -> new VaroStateTrigger<>(GameState.STARTING)),
    RUNNING_TRIGGER(() -> new VaroStateTrigger<>(GameState.RUNNING, new RunningLoginTask())),
    // TODO: Decide whether only the default or all triggers should be added to mass recording
    MASS_RECORDING_TRIGGER(DefaultGameTrigger.RUNNING_TRIGGER,  () -> new VaroStateTrigger<>(GameState.MASS_RECORDING)),
    FINISHED_TRIGGER(() -> new VaroStateTrigger<>(GameState.FINISHED));

    private final DefaultGameTrigger copyTasks;
    private final Supplier<VaroTaskTrigger<VaroRegisterInfo>> trigger;

    DefaultGameTrigger(DefaultGameTrigger copyTasks, Supplier<VaroTaskTrigger<VaroRegisterInfo>> trigger) {
        this.copyTasks = copyTasks;
        this.trigger = trigger;
    }

    DefaultGameTrigger(Supplier<VaroTaskTrigger<VaroRegisterInfo>> trigger) {
        this(null, trigger);
    }

    @Override
    public VaroTaskTrigger<VaroRegisterInfo> createTrigger() {
        VaroTaskTrigger<VaroRegisterInfo> trigger = this.trigger.get();
        if (this.copyTasks != null) this.copyTasks.createTrigger().getTasks().forEach(trigger::addTask);
        return trigger;
    }

    @Override
    public void addTask(Supplier<VaroTask<VaroRegisterInfo>> taskSupplier) {
        // TODO: Implement
    }
}