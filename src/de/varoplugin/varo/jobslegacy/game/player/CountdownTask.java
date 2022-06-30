package de.varoplugin.varo.jobslegacy.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 *
 */
public class CountdownTask extends HeartbeatTask {

    public CountdownTask(VaroPlayer player) {
        super(player);
    }

    @Override
    public void run() {
        // TODO: Decrease countdown
    }
}