package de.varoplugin.varo.game.entity.player.tasks;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.tasks.VaroStateTask;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;

/**
 * Represents a player task.
 * Unregisters if the player is being removed.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerTask extends VaroStateTask {

    protected final VaroPlayer player;

    public VaroPlayerTask(VaroPlayer player) {
        super(player.getVaro());

        this.player = player;
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (this.player.equals(event.getPlayer())) this.unregister();
    }
}
