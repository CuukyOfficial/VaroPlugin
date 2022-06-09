package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.tasks.AbstractTaskTrigger;
import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.register.VaroPlayerTaskInfo;
import org.bukkit.event.EventHandler;

/**
 * Triggers all player specific tasks.
 * Unregisters the task on player remove.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class PlayerTrigger<I extends VaroPlayerTaskInfo> extends AbstractTaskTrigger<I> implements VaroPlayerTrigger<I> {

    @SafeVarargs
    public PlayerTrigger(VaroTask<I>... children) {
        super(children);
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (!event.getPlayer().equals(this.getInfo().getPlayer())) return;
        this.unregister();
    }
}