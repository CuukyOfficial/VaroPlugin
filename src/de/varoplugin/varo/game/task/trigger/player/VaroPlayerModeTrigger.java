package de.varoplugin.varo.game.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerModeChangeEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;
import de.varoplugin.varo.task.trigger.VaroTrigger;
import org.bukkit.event.EventHandler;

public class VaroPlayerModeTrigger extends AbstractPlayerTrigger {

    private VaroPlayerMode mode;

    public VaroPlayerModeTrigger(VaroPlayer player, VaroPlayerMode mode, boolean match) {
        super(player, match);
        this.mode = mode;
    }

    public VaroPlayerModeTrigger(VaroPlayer player, VaroPlayerMode mode) {
        this(player, mode, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.getPlayer().getMode().equals(this.mode);
    }

    @EventHandler
    public void onPlayerStateChange(VaroPlayerModeChangeEvent event) {
        if (!this.getPlayer().equals(event.getPlayer())) return;
        this.triggerIf(event.getMode().equals(this.mode));
    }

    @Override
    public VaroTrigger clone() {
        VaroPlayerModeTrigger trigger = (VaroPlayerModeTrigger) super.clone();
        trigger.mode = this.mode;
        return trigger;
    }
}
