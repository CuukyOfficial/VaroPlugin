package de.varoplugin.varo.game.task.trigger.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.entity.player.Player;
import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.task.trigger.GameTrigger;
import org.bukkit.event.EventHandler;

public abstract class AbstractPlayerTrigger extends GameTrigger {

    private Player player;

    public AbstractPlayerTrigger(Player player, boolean match) {
        super(player.getVaro(), match);
        this.player = player;
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (!this.player.equals(event.getPlayer())) return;
        this.deactivate();
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public Trigger clone() {
        AbstractPlayerTrigger trigger = (AbstractPlayerTrigger) super.clone();
        trigger.player = this.player;
        return trigger;
    }
}
