package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerMode;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerModeChangeEvent extends VaroPlayerCancelableEvent {

    private final VaroPlayerMode mode;

    public VaroPlayerModeChangeEvent(VaroPlayer player, VaroPlayerMode mode) {
        super(player);

        this.mode = mode;
    }

    public VaroPlayerMode getMode() {
        return this.mode;
    }
}