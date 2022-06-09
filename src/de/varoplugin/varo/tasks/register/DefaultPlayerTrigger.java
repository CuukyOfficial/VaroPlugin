package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTriggerHolder;
import de.varoplugin.varo.tasks.game.player.NoMoveTask;
import de.varoplugin.varo.tasks.game.player.PlayerOnlineTrigger;
import de.varoplugin.varo.tasks.game.player.PlayerStateTrigger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultPlayerTrigger implements VaroTriggerHolder<VaroPlayerTaskInfo> {

    LOBBY_PLAYER(() -> new PlayerStateTrigger<>(GameState.LOBBY, new PlayerOnlineTrigger<>(true)), NoMoveTask::new);

    private final DefaultPlayerTrigger parent;
    private final Supplier<VaroTaskTrigger<VaroPlayerTaskInfo>> trigger;
    private final List<Supplier<VaroTask<VaroPlayerTaskInfo>>> tasks;

    @SafeVarargs
    DefaultPlayerTrigger(DefaultPlayerTrigger parent, Supplier<VaroTaskTrigger<VaroPlayerTaskInfo>> trigger, Supplier<VaroTask<VaroPlayerTaskInfo>>... tasks) {
        this.parent = parent;
        this.trigger = trigger;
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    @SafeVarargs
    DefaultPlayerTrigger(Supplier<VaroTaskTrigger<VaroPlayerTaskInfo>> trigger, Supplier<VaroTask<VaroPlayerTaskInfo>>... tasks) {
        this(null, trigger, tasks);
    }

    @Override
    public VaroTaskTrigger<VaroPlayerTaskInfo> createTrigger() {
        VaroTaskTrigger<VaroPlayerTaskInfo> trigger = this.trigger.get();
        this.tasks.stream().map(Supplier::get).forEach(trigger::addTask);
        if (this.parent != null) {
            VaroTaskTrigger<VaroPlayerTaskInfo> pTrigger = this.parent.createTrigger();
            pTrigger.hook(trigger);
            trigger = pTrigger;
        }
        return trigger;
    }

    @Override
    public void addTask(Supplier<VaroTask<VaroPlayerTaskInfo>> taskSupplier) {
        this.tasks.add(taskSupplier);
    }
}