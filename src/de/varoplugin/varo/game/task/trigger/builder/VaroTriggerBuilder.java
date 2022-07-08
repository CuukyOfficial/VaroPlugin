package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.api.task.trigger.builder.LayeredTriggerBuilder;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.task.trigger.GameStateTrigger;
import de.varoplugin.varo.game.task.trigger.VaroConfigTrigger;

public class VaroTriggerBuilder implements IVaroTriggerBuilder {

    private final TriggerBuilder internal;
    private final Varo varo;

    public VaroTriggerBuilder(Varo varo) {
        this.internal = new LayeredTriggerBuilder(varo.getPlugin());
        this.varo = varo;
    }

    @Override
    public IVaroTriggerBuilder when(VaroState state) {
        this.internal.when(new GameStateTrigger(this.varo, state));
        return this;
    }

    // TODO: Remove?
    @Override
    public IVaroTriggerBuilder whenNot(VaroState state) {
        return this.when(new GameStateTrigger(this.varo, state, false));
    }

    @Override
    public VaroTrigger build() {
        return this.internal.build();
    }

    @Override
    public VaroTrigger complete() {
        return this.internal.complete();
    }

    @Override
    public IVaroTriggerBuilder when(VaroTrigger trigger) {
        this.internal.when(trigger);
        return this;
    }

    @Override
    public IVaroTriggerBuilder when(TriggerBuilder when) {
        this.internal.when(when);
        return this;
    }

    @Override
    public IVaroTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry) {
        this.internal.and(new VaroConfigTrigger(this.varo, entry));
        return this;
    }

    @Override
    public IVaroTriggerBuilder and(VaroTrigger triggers) {
        this.internal.and(triggers);
        return this;
    }

    @Override
    public IVaroTriggerBuilder and(TriggerBuilder and) {
        this.internal.and(and);
        return this;
    }

    @Override
    public IVaroTriggerBuilder down() {
        this.internal.down();
        return this;
    }

    @Override
    public IVaroTriggerBuilder ground() {
        this.internal.ground();
        return this;
    }
}
