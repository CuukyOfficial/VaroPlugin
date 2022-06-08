package de.varoplugin.varo.game.tasks.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.tasks.VaroTask;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerTask extends VaroTask {

    protected final VaroPlayer player;

    public VaroPlayerTask(VaroPlayer player) {
        this.player = player;
    }
}