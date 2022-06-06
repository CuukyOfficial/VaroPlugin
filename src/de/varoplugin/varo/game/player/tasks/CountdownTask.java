package de.varoplugin.varo.game.player.tasks;

import de.varoplugin.varo.game.player.VaroPlayer;

/**
 *
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class CountdownTask extends OnlineHeartbeatTask {

    public CountdownTask(VaroPlayer player) {
        super(player);
    }

    @Override
    public void run() {
        // TODO: Decrease countdown
    }
}