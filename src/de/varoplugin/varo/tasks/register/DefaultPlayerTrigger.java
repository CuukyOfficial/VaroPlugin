package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTriggerHolder;
import de.varoplugin.varo.tasks.game.VaroStateTrigger;
import de.varoplugin.varo.tasks.game.player.NoMoveTask;
import de.varoplugin.varo.tasks.game.player.PlayerOnlineTrigger;

import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultPlayerTrigger implements VaroTriggerHolder<VaroPlayerTaskInfo> {

    LOBBY_ONLINE_PLAYER(() -> new VaroStateTrigger<>(GameState.LOBBY, new PlayerOnlineTrigger<>(true, new NoMoveTask())));

    private final Supplier<VaroTaskTrigger<VaroPlayerTaskInfo>> trigger;

    DefaultPlayerTrigger(Supplier<VaroTaskTrigger<VaroPlayerTaskInfo>> trigger) {
        this.trigger = trigger;
    }

    @Override
    public VaroTaskTrigger<VaroPlayerTaskInfo> createTrigger() {
        return this.trigger.get();
    }

    @Override
    public void addTask(Supplier<VaroTask<VaroPlayerTaskInfo>> taskSupplier) {
        // TODO: Implement
    }
}