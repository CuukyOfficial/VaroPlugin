package de.varoplugin.varo.game.task.trigger;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.game.Varo;

public class VaroConfigTrigger extends GameTrigger {

    private VaroConfig.VaroBoolConfigEntry entry;

    public VaroConfigTrigger(Varo varo, VaroConfig.VaroBoolConfigEntry entry, boolean match) {
        super(varo, match);
        this.entry = entry;
    }

    public VaroConfigTrigger(Varo varo, VaroConfig.VaroBoolConfigEntry entry) {
        this(varo, entry, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.entry.getValue();
    }

    // TODO: Varo config entry change event

    @Override
    public Trigger clone() {
        VaroConfigTrigger trigger = (VaroConfigTrigger) super.clone();
        trigger.entry = this.entry;
        return trigger;
    }
}
