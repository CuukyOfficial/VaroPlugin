package de.varoplugin.varo.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.PlayerRemoveEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.task.trigger.GameTrigger;
import org.bukkit.event.EventHandler;

public abstract class AbstractPlayerTrigger extends GameTrigger {

    private VaroPlayer player;

    public AbstractPlayerTrigger(VaroPlayer player, boolean match) {
        super(player.getVaro(), match);
        this.player = player;
    }

    @EventHandler
    public void onPlayerRemove(PlayerRemoveEvent event) {
        if (!this.player.equals(event.getPlayer())) return;
        this.deactivate();
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Trigger clone() {
        AbstractPlayerTrigger trigger = (AbstractPlayerTrigger) super.clone();
        trigger.player = this.player;
        return trigger;
    }
}
