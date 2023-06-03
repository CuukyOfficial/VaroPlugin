package de.cuuky.varo.api.player;

import de.cuuky.varo.api.VaroEvent;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroPlayerEvent extends VaroEvent {

    private final VaroPlayer player;

    public VaroPlayerEvent(VaroPlayer player) {
        this.player = player;
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}
