package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.game.task.trigger.player.VaroOnlineTrigger;
import de.varoplugin.varo.game.task.trigger.player.VaroParticipantStateTrigger;
import de.varoplugin.varo.game.task.trigger.player.VaroPlayerModeTrigger;

public class VaroPlayerTriggerBuilder implements IVaroPlayerTriggerBuilder {

    private final VaroPlayer player;
    private final IVaroTriggerBuilder internal;

    public VaroPlayerTriggerBuilder(VaroPlayer player) {
        this.internal = new VaroTriggerBuilder(player.getVaro());
        this.player = player;
    }

    @Override
    public IVaroPlayerTriggerBuilder when(VaroParticipantState state) {
        this.internal.when(new VaroParticipantStateTrigger(this.player, state));
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder when(VaroPlayerMode mode) {
        this.internal.when(new VaroPlayerModeTrigger(this.player, mode));
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder when(boolean online) {
        this.internal.when(new VaroOnlineTrigger(this.player, online));
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder and(VaroParticipantState alive) {
        this.internal.and(new VaroParticipantStateTrigger(this.player, alive));
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder and(boolean online) {
        this.internal.and(new VaroOnlineTrigger(this.player, online));
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder when(VaroState state) {
        this.internal.when(state);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder whenNot(VaroState state) {
        this.internal.whenNot(state);
        return this;
    }

    @Override
    public Trigger build() {
        return this.internal.build();
    }

    @Override
    public Trigger complete() {
        return this.internal.complete();
    }

    @Override
    public IVaroPlayerTriggerBuilder when(Trigger trigger) {
        this.internal.when(trigger);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder when(TriggerBuilder when) {
        this.internal.when(when);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry) {
        this.internal.and(entry);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder and(Trigger triggers) {
        this.internal.and(triggers);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder and(TriggerBuilder and) {
        this.internal.and(and);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder down() {
        this.internal.down();
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder ground() {
        this.internal.ground();
        return this;
    }
}
