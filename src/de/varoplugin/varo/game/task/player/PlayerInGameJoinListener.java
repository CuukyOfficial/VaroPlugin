package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerInGameJoinListener extends AbstractPlayerListener {

    public PlayerInGameJoinListener(VaroPlayer player) {
        super(player);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        if (this.getPlayer().getCountdown() == 0) this.getPlayer().setCountdown(300); // TODO: Configurable
    }
}
