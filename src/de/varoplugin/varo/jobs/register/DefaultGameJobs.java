package de.varoplugin.varo.jobs.register;

import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.jobs.VaroTrigger;
import de.varoplugin.varo.jobs.game.LobbyLoginListener;
import de.varoplugin.varo.jobs.game.RunningLoginListener;
import de.varoplugin.varo.jobs.game.VaroStateTrigger;

import java.util.function.Supplier;

/**
 * Contains all default game trigger.
 */
public enum DefaultGameJobs {

    LOBBY_TRIGGER(() -> new VaroStateTrigger(GameState.LOBBY, new LobbyLoginListener())),
    STARTING_TRIGGER(() -> new VaroStateTrigger(GameState.STARTING, new RunningLoginListener())),
    RUNNING_TRIGGER(() -> new VaroStateTrigger(GameState.RUNNING, new RunningLoginListener())),
    // TODO: Decide whether only the default or all triggers should be added to mass recording
    MASS_RECORDING_TRIGGER(DefaultGameJobs.RUNNING_TRIGGER,  () -> new VaroStateTrigger(GameState.MASS_RECORDING)),
    FINISHED_TRIGGER(() -> new VaroStateTrigger(GameState.FINISHED, new RunningLoginListener()));

    private final DefaultGameJobs copyTasks;
    private final Supplier<VaroTrigger> trigger;

    DefaultGameJobs(DefaultGameJobs copyTasks, Supplier<VaroTrigger> trigger) {
        this.copyTasks = copyTasks;
        this.trigger = trigger;
    }

    DefaultGameJobs(Supplier<VaroTrigger> trigger) {
        this(null, trigger);
    }

    public VaroTrigger createTrigger() {
        VaroTrigger trigger = this.trigger.get();
        if (this.copyTasks != null) trigger.addJobsTo(this.copyTasks.createTrigger());
        return trigger;
    }
}