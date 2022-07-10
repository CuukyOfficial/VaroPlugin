package de.varoplugin.varo.game.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.PlayerModeChangeEvent;
import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.game.entity.player.PlayerMode;
import de.varoplugin.varo.api.task.trigger.Trigger;
import org.bukkit.event.EventHandler;

public class VaroPlayerModeTrigger extends AbstractPlayerTrigger {

    private PlayerMode mode;

    public VaroPlayerModeTrigger(Player player, PlayerMode mode, boolean match) {
        super(player, match);
        this.mode = mode;
    }

    public VaroPlayerModeTrigger(Player player, PlayerMode mode) {
        this(player, mode, true);
    }

    @Override
    protected boolean isTriggered() {
        return this.getPlayer().getMode().equals(this.mode);
    }

    @EventHandler
    public void onPlayerStateChange(PlayerModeChangeEvent event) {
        if (!this.getPlayer().equals(event.getPlayer())) return;
        this.triggerIf(event.getMode().equals(this.mode));
    }

    @Override
    public Trigger clone() {
        VaroPlayerModeTrigger trigger = (VaroPlayerModeTrigger) super.clone();
        trigger.mode = this.mode;
        return trigger;
    }
}
