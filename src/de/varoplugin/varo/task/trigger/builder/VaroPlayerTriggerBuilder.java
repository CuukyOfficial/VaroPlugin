package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.task.trigger.VaroTrigger;
import de.varoplugin.varo.task.trigger.game.player.VaroOnlineTrigger;
import de.varoplugin.varo.task.trigger.game.player.VaroParticipantStateTrigger;
import de.varoplugin.varo.task.trigger.game.player.VaroPlayerModeTrigger;

public class VaroPlayerTriggerBuilder extends AbstractTriggerBuilder<VaroPlayer> implements TriggerBuilder<VaroPlayer> {

    public VaroPlayerTriggerBuilder or(VaroParticipantState state) {
        super.orTrigger(vp -> new VaroParticipantStateTrigger(vp, state));
        return this;
    }

    public VaroPlayerTriggerBuilder or(VaroPlayerMode mode) {
        super.orTrigger(vp -> new VaroPlayerModeTrigger(vp, mode));
        return this;
    }

    public VaroPlayerTriggerBuilder or(boolean online) {
        super.orTrigger(vp -> new VaroOnlineTrigger(vp, online));
        return this;
    }

    @Override
    public VaroPlayerTriggerBuilder or(VaroTrigger trigger) {
        super.or(trigger);
        return this;
    }

    @Override
    public VaroPlayerTriggerBuilder and(VaroTrigger... trigger) {
        super.and(trigger);
        return this;
    }
}
