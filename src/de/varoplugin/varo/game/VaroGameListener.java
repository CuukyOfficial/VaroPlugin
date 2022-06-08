package de.varoplugin.varo.game;

import de.varoplugin.varo.game.tasks.DebugTask;
import de.varoplugin.varo.game.tasks.LobbyLoginTask;
import de.varoplugin.varo.game.tasks.TaskRegistrable;
import de.varoplugin.varo.game.tasks.VaroStateTrigger;
import de.varoplugin.varo.game.tasks.VaroTaskTrigger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum VaroGameListener {

    LOBBY_LISTENER(() -> new VaroStateTrigger(VaroGameState.LOBBY, new LobbyLoginTask())),
    RUNNING_LISTENER(() -> new VaroStateTrigger(VaroGameState.RUNNING, new DebugTask())); // Debug

    private final Supplier<VaroTaskTrigger> trigger;
    private final List<TaskRegistrable> tasks;

    VaroGameListener(Supplier<VaroTaskTrigger> trigger) {
        this.trigger = trigger;
        this.tasks = new ArrayList<>();
    }

    public VaroTaskTrigger getTrigger() {
        VaroTaskTrigger trigger = this.trigger.get();
        this.tasks.forEach(trigger::addTask);
        return trigger;
    }

    public void addTask(TaskRegistrable task) {
        this.tasks.add(task);
    }
}