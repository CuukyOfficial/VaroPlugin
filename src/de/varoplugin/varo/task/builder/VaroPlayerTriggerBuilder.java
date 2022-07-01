package de.varoplugin.varo.task.builder;

import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.task.VaroTrigger;

public interface VaroPlayerTriggerBuilder extends TriggerBuilder<VaroPlayer> {

    VaroPlayerTriggerBuilder or(VaroParticipantState state);

    VaroPlayerTriggerBuilder or(VaroPlayerMode mode);

    VaroPlayerTriggerBuilder or(boolean online);

    @Override
    VaroPlayerTriggerBuilder or(VaroTrigger trigger);

    @Override
    VaroPlayerTriggerBuilder and(VaroTrigger... trigger);
}
