package de.cuuky.varo.heartbeat;

import de.cuuky.varo.Heartbeat;
import de.cuuky.varo.Varo;
import de.cuuky.varo.entity.player.VaroPlayer;

public class UpdateHeartbeat extends Heartbeat {

    public UpdateHeartbeat(Varo varo) {
        super(varo);
    }

    @Override
    public void heartbeat(VaroPlayer player) {
        player.update();
    }
}