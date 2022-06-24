package de.varoplugin.varo.jobs.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobs.AbstractTaskTrigger;
import de.varoplugin.varo.jobs.VaroJob;
import org.bukkit.event.EventHandler;

/**
 * Triggers all player specific tasks.
 * Unregisters the task on player remove.
 */
public abstract class PlayerTrigger extends AbstractTaskTrigger {

    private final VaroPlayer player;
    public PlayerTrigger(VaroPlayer player, VaroJob... children) {
        super(children);

        this.player = player;
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (!event.getPlayer().equals(this.player)) return;
        this.unregister();
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}