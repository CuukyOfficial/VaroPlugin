package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.game.entity.player.OnlineState;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.PlayerMode;

import java.util.function.Predicate;

public interface IVaroPlayerTriggerBuilder extends IVaroTriggerBuilder {

    IVaroPlayerTriggerBuilder whenPState(Predicate<ParticipantState> state);

    IVaroPlayerTriggerBuilder whenMode(Predicate<PlayerMode> mode);

    IVaroPlayerTriggerBuilder whenOnline(Predicate<OnlineState> online);

    IVaroPlayerTriggerBuilder andOnline(Predicate<OnlineState> online);

    IVaroPlayerTriggerBuilder andPState(Predicate<ParticipantState> alive);

    IVaroPlayerTriggerBuilder whenState(Predicate<State> allowed);

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
