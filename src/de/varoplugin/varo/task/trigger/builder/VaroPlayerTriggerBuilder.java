package de.varoplugin.varo.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.api.task.trigger.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.State;
import de.varoplugin.varo.game.entity.player.OnlineState;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.PlayerMode;
import de.varoplugin.varo.task.trigger.player.VaroOnlineTrigger;
import de.varoplugin.varo.task.trigger.player.VaroParticipantStateTrigger;
import de.varoplugin.varo.task.trigger.player.VaroPlayerModeTrigger;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class VaroPlayerTriggerBuilder implements IVaroPlayerTriggerBuilder {

    private final VaroPlayer player;
    private final IVaroTriggerBuilder internal;

    private Stream<ParticipantState> getParticipantStates() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Stream<PlayerMode> getModes() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Stream<OnlineState> getOnlineStates() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public VaroPlayerTriggerBuilder(VaroPlayer player) {
        this.internal = new VaroTriggerBuilder(player.getVaro());
        this.player = player;
    }

    private void when(ParticipantState state) {
        this.internal.when(new VaroParticipantStateTrigger(this.player, state));
    }

    private void when(PlayerMode mode) {
        this.internal.when(new VaroPlayerModeTrigger(this.player, mode));
    }

    private void when(OnlineState online) {
        this.internal.when(new VaroOnlineTrigger(this.player, online));
    }

    private void and(ParticipantState alive) {
        this.internal.and(new VaroParticipantStateTrigger(this.player, alive));
    }

    private void and(OnlineState online) {
        this.internal.and(new VaroOnlineTrigger(this.player, online));
    }

    @Override
    public IVaroPlayerTriggerBuilder whenPState(Predicate<ParticipantState> state) {
        this.getParticipantStates().filter(state).forEach(this::when);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder whenMode(Predicate<PlayerMode> mode) {
        this.getModes().filter(mode).forEach(this::when);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder whenOnline(Predicate<OnlineState> online) {
        this.getOnlineStates().filter(online).forEach(this::when);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder andOnline(Predicate<OnlineState> online) {
        this.getOnlineStates().filter(online).forEach(this::and);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder andPState(Predicate<ParticipantState> alive) {
        this.getParticipantStates().filter(alive).forEach(this::and);
        return this;
    }

    @Override
    public IVaroPlayerTriggerBuilder whenState(Predicate<State> allowed) {
        this.internal.whenState(allowed);
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
