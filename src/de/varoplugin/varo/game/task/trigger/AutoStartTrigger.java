package de.varoplugin.varo.game.task.trigger;

import de.varoplugin.varo.api.event.game.VaroAutoStartChangedEvent;
import de.varoplugin.varo.game.Varo;
import org.bukkit.event.EventHandler;

public class AutoStartTrigger extends GameTrigger {

    public AutoStartTrigger(Varo varo) {
        super(varo, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.getVaro().getAutoStart() != null;
    }

    @EventHandler
    public void onAutoStartChange(VaroAutoStartChangedEvent event) {
        this.triggerIf(event.getNewAutoStart() != null);
    }
}
