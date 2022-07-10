package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.PlayerMode;

public interface IVaroPlayerTriggerBuilder extends IVaroTriggerBuilder {

    IVaroPlayerTriggerBuilder when(ParticipantState state);

    IVaroPlayerTriggerBuilder when(PlayerMode mode);

    IVaroPlayerTriggerBuilder when(boolean online);

    IVaroPlayerTriggerBuilder and(boolean online);

    IVaroPlayerTriggerBuilder when(State state);

    IVaroPlayerTriggerBuilder whenNot(State state);

    IVaroPlayerTriggerBuilder and(ParticipantState alive);

    Trigger build();

    Trigger complete();

    IVaroPlayerTriggerBuilder when(Trigger trigger);

    IVaroPlayerTriggerBuilder when(TriggerBuilder when);

    IVaroPlayerTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry);

    IVaroPlayerTriggerBuilder and(Trigger triggers);

    IVaroPlayerTriggerBuilder and(TriggerBuilder and);

    IVaroPlayerTriggerBuilder down();

    IVaroPlayerTriggerBuilder ground();
}
