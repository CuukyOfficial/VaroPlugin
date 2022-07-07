package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.task.trigger.player.VaroOnlineTrigger;
import de.varoplugin.varo.game.task.trigger.player.VaroParticipantStateTrigger;
import de.varoplugin.varo.game.task.trigger.player.VaroPlayerModeTrigger;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;

import java.util.function.Consumer;

public class VaroPlayerTriggerBuilder extends VaroTriggerBuilder implements TriggerBuilder {

    private final VaroPlayer player;

    public VaroPlayerTriggerBuilder(VaroPlayer player) {
        super(player.getVaro());
        this.player = player;
    }

    public VaroPlayerTriggerBuilder when(VaroParticipantState state) {
        return (VaroPlayerTriggerBuilder) super.when(new VaroParticipantStateTrigger(this.player, state));
    }

    public VaroPlayerTriggerBuilder when(VaroPlayerMode mode) {
        return (VaroPlayerTriggerBuilder) super.when(new VaroPlayerModeTrigger(this.player, mode));
    }

    public VaroPlayerTriggerBuilder when(boolean online) {
        return (VaroPlayerTriggerBuilder) super.when(new VaroOnlineTrigger(this.player, online));
    }

    public VaroPlayerTriggerBuilder and(boolean online) {
        return (VaroPlayerTriggerBuilder) super.and(new VaroOnlineTrigger(this.player, online));
    }

    @Override
    public VaroPlayerTriggerBuilder when(VaroState state) {
        return (VaroPlayerTriggerBuilder) super.when(state);
    }

    @Override
    public VaroPlayerTriggerBuilder when(VaroTrigger trigger) {
        return (VaroPlayerTriggerBuilder) super.when(trigger);
    }

    @Override
    public VaroPlayerTriggerBuilder and(VaroTrigger... trigger) {
        return (VaroPlayerTriggerBuilder) super.and(trigger);
    }

    @Override
    public VaroPlayerTriggerBuilder and(VaroTriggerBuilder and) {
        return (VaroPlayerTriggerBuilder) super.and(and);
    }

    public VaroPlayerTriggerBuilder andVp(Consumer<VaroPlayerTriggerBuilder> and) {
        VaroPlayerTriggerBuilder varoPlayerTriggerBuilder = new VaroPlayerTriggerBuilder(this.player);
        and.accept(varoPlayerTriggerBuilder);
        return this.and(varoPlayerTriggerBuilder);
    }
}
