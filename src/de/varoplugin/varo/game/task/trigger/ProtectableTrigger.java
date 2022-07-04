package de.varoplugin.varo.game.task.trigger;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableRemoveEvent;
import de.varoplugin.varo.api.task.trigger.VaroTrigger;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.event.EventHandler;

public class ProtectableTrigger extends GameTrigger {

    private VaroProtectable protectable;

    public ProtectableTrigger(VaroProtectable protectable, boolean match) {
        super(protectable.getVaro(), match);
        this.protectable = protectable;
    }

    public ProtectableTrigger(VaroProtectable protectable) {
        this(protectable, true);
    }

    @EventHandler
    public void onProtectableRemove(VaroProtectableRemoveEvent event) {
        if (event.getProtectable().equals(this.protectable)) this.destroy();
    }

    @Override
    protected boolean isTriggered() {
        return true;
    }

    @Override
    public VaroTrigger clone() {
        ProtectableTrigger trigger = (ProtectableTrigger) super.clone();
        trigger.protectable = protectable;
        return trigger;
    }
}
