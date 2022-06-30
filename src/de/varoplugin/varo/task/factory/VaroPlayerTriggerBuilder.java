package de.varoplugin.varo.task.factory;

import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.task.VaroTrigger;

public interface VaroPlayerTriggerBuilder extends TriggerFactory {

    VaroPlayerTriggerBuilder or(VaroParticipantState state);

    VaroPlayerTriggerBuilder or(VaroPlayerMode mode);

    VaroPlayerTriggerBuilder or(boolean online);

    VaroPlayerTriggerBuilder and(VaroTrigger trigger);

    @Override
    VaroPlayerTriggerBuilder and(TriggerFactory factory);

}
