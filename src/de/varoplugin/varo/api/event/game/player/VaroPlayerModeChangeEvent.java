package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;

public class VaroPlayerModeChangeEvent extends VaroPlayerCancelableEvent {

    private VaroPlayerMode mode;

    public VaroPlayerModeChangeEvent(VaroPlayer player, VaroPlayerMode mode) {
        super(player);

        this.mode = mode;
    }

    public void setMode(VaroPlayerMode mode) {
        this.mode = mode;
    }

    public VaroPlayerMode getMode() {
        return this.mode;
    }
}