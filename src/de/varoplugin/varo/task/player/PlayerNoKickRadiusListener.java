package de.varoplugin.varo.task.player;

import de.varoplugin.varo.api.event.game.player.PlayerCountdownChangeEvent;
import de.varoplugin.varo.game.entity.player.Player;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;

public class PlayerNoKickRadiusListener extends AbstractPlayerListener {

    private final int checkCountdown;
    private final int checkRadius;

    public PlayerNoKickRadiusListener(Player player) {
        super(player);

        this.checkCountdown = 5;
        this.checkRadius = 30;
        // TODO: Configurable
    }

    @EventHandler
    public void onPlayerKick(PlayerCountdownChangeEvent event) {
        if (!event.getPlayer().equals(this.getPlayer()) || event.getCountdown() >= this.checkCountdown) return;

        for (Entity entity : event.getPlayer().getPlayer().getNearbyEntities(this.checkRadius, this.checkRadius, this.checkRadius)) {
            if (entity.getType() != EntityType.PLAYER) return;
            Player other = this.getVaro().getPlayer(entity.getUniqueId());
            if (other.getTeam() != this.getPlayer().getTeam()) {
                // TODO: Add event
                event.setCountdown(this.checkCountdown);
            }
        }
    }
}
