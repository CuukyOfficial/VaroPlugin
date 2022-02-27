package de.cuuky.varo.heartbeat;

import de.cuuky.varo.Varo;
import de.cuuky.varo.entity.player.VaroPlayer;

public class RunningHeartbeat extends UpdateHeartbeat {

    public RunningHeartbeat(Varo varo) {
        super(varo);
    }

    @Override
    public void heartbeat(VaroPlayer player) {

        // update player time etc

        super.heartbeat(player);
    }
}