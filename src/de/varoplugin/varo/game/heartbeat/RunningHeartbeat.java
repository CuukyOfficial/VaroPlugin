package de.varoplugin.varo.game.heartbeat;

import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class RunningHeartbeat extends AbstractHeartbeat {

    @Override
    public void run() {
        for (VaroPlayer player : this.varo.getPlayers()) {
            if (!player.isOnline()) continue;

            // TODO: Increase countdown, update distance to border etc.
        }
    }
}