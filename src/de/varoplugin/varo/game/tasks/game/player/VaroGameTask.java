package de.varoplugin.varo.game.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroGameTask extends de.varoplugin.varo.game.tasks.VaroGameTask {

    protected final VaroPlayer player;

    public VaroGameTask(VaroPlayer player) {
        this.player = player;
    }
}