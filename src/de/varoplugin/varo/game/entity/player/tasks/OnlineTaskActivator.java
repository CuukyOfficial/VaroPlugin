package de.varoplugin.varo.game.entity.player.tasks;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class OnlineTaskActivator extends VaroPlayerStateTask {

    private final VaroPlayerOnlineTask task;

    public OnlineTaskActivator(VaroPlayer player, VaroPlayerOnlineTask task) {
        super(player);

        this.task = task;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getUniqueId().equals(this.player.getUuid())) this.task.register();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().getUniqueId().equals(this.player.getUuid())) this.task.unregisterOnlySelf();
    }
}