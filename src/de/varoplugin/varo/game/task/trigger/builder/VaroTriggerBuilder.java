package de.varoplugin.varo.game.task.trigger.builder;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.task.trigger.GameStateTrigger;
import de.varoplugin.varo.api.task.trigger.builder.AbstractTriggerBuilder;
import de.varoplugin.varo.api.task.trigger.builder.TriggerBuilder;

import java.util.function.Consumer;

public class VaroTriggerBuilder extends AbstractTriggerBuilder implements TriggerBuilder {

    private final Varo varo;

    public VaroTriggerBuilder(Varo varo) {
        super(varo.getPlugin());
        this.varo = varo;
    }

    public VaroTriggerBuilder when(VaroState state) {
        this.when(new GameStateTrigger(this.varo, state));
        return this;
    }

    public VaroTriggerBuilder whenNot(VaroState state) {
        this.when(new GameStateTrigger(this.varo, state, false));
        return this;
    }

    @Override
    public VaroTriggerBuilder when(VaroTrigger trigger) {
        super.when(trigger);
        return this;
    }

    @Override
    public VaroTriggerBuilder and(VaroTrigger... triggers) {
        super.and(triggers);
        return this;
    }

    public VaroTriggerBuilder and(Consumer<VaroTriggerBuilder> and) {
        VaroTriggerBuilder vtb = new VaroTriggerBuilder(this.varo);
        and.accept(vtb);
        this.and(vtb);
        return this;
    }
}
