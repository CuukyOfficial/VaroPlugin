package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;

public interface IVaroPlayerTriggerBuilder extends IVaroTriggerBuilder {

    IVaroPlayerTriggerBuilder when(VaroParticipantState state);

    IVaroPlayerTriggerBuilder when(VaroPlayerMode mode);

    IVaroPlayerTriggerBuilder when(boolean online);

    IVaroPlayerTriggerBuilder and(boolean online);

    IVaroPlayerTriggerBuilder when(VaroState state);

    IVaroPlayerTriggerBuilder whenNot(VaroState state);

    IVaroPlayerTriggerBuilder and(VaroParticipantState alive);

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
