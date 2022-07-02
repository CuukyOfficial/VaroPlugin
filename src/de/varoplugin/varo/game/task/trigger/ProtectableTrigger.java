package de.varoplugin.varo.game.task.trigger;

import de.varoplugin.varo.api.event.game.world.protectable.VaroProtectableRemoveEvent;
import de.varoplugin.varo.game.world.protectable.VaroProtectable;
import org.bukkit.event.EventHandler;

public class ProtectableTrigger extends GameTrigger {

    private VaroProtectable protectable;

    public ProtectableTrigger(VaroProtectable protectable, boolean match) {
        super(protectable.getVaro(), match);
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
}
