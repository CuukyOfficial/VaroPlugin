package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.task.trigger.GameStateTrigger;
import de.varoplugin.varo.api.task.trigger.builder.AbstractTriggerBuilder;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;
import de.varoplugin.varo.game.task.trigger.VaroConfigTrigger;

import java.util.function.Consumer;

public class VaroTriggerBuilder extends AbstractTriggerBuilder implements TriggerBuilder {

    private final Varo varo;

    public VaroTriggerBuilder(Varo varo) {
        super(varo.getPlugin());
        this.varo = varo;
    }

    public VaroTriggerBuilder when(VaroState state) {
        return this.when(new GameStateTrigger(this.varo, state));
    }

    public VaroTriggerBuilder whenNot(VaroState state) {
        return this.when(new GameStateTrigger(this.varo, state, false));
    }

    @Override
    public VaroTriggerBuilder when(VaroTrigger trigger) {
        return (VaroTriggerBuilder) super.when(trigger);
    }

    @Override
    public VaroTriggerBuilder and(VaroTrigger... triggers) {
        return (VaroTriggerBuilder) super.and(triggers);
    }

    public VaroTriggerBuilder and(VaroConfig.VaroBoolConfigEntry entry) {
        return (VaroTriggerBuilder) super.and(new VaroConfigTrigger(this.varo, entry));
    }

    public VaroTriggerBuilder andTb(Consumer<VaroTriggerBuilder> and) {
        VaroTriggerBuilder vtb = new VaroTriggerBuilder(this.varo);
        and.accept(vtb);
        return (VaroTriggerBuilder) this.and(vtb);
    }
}
