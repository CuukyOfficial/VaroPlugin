package de.varoplugin.varo.game.entity.player.tasks;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.Location;

// Not sure if this should be a heartbeat task.
/**
 * Resets the position of the player if he has moved.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class NoMoveTask extends OnlineHeartbeatTask {

    private Location lastLocation;

    public NoMoveTask(VaroPlayer player) {
        super(player, 0L, 5L);
    }

    @Override
    public void run() {
        Location current = this.player.getPlayer().getLocation();
        if (this.lastLocation != null && (current.getBlockX() != this.lastLocation.getBlockX()
            || current.getBlockZ() != this.lastLocation.getBlockZ())) {
            this.player.getPlayer().teleport(this.lastLocation);
        } else {
            this.lastLocation = current.clone();
        }
    }
}