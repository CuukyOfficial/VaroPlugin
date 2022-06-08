package de.varoplugin.varo.game.tasks.game;

import de.varoplugin.varo.game.VaroGameState;
import de.varoplugin.varo.game.tasks.TaskTrigger;
import de.varoplugin.varo.game.tasks.VaroStateTrigger;
import de.varoplugin.varo.game.tasks.VaroTask;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultGameTrigger implements Listener {

    LOBBY_TRIGGER(() -> new VaroStateTrigger(VaroGameState.LOBBY, new LobbyLoginTask())),
    RUNNING_TRIGGER(() -> new VaroStateTrigger(VaroGameState.RUNNING, new RunningLoginTask())),
    // TODO: Make it work
    MASS_RECORDING_TRIGGER(RUNNING_TRIGGER.trigger),
    FINISHED_TRIGGER(() -> new VaroStateTrigger(VaroGameState.FINISHED));

    private final Supplier<TaskTrigger> trigger;
    // TODO: Add adder method
    private final List<VaroTask> tasks;

    DefaultGameTrigger(Supplier<TaskTrigger> trigger) {
        this.trigger = trigger;
        this.tasks = new ArrayList<>();
    }

    public TaskTrigger getTrigger() {
        TaskTrigger trigger = this.trigger.get();
        this.tasks.forEach(trigger::addTask);
        return trigger;
    }
}