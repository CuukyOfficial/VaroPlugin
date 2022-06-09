package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.VaroTask;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerTask extends VaroTask {

    @Override
    default boolean isInitialized() {
        return this.getPlayer() != null;
    }

    default boolean register(VaroPlayer player) {
        this.setPlayer(player);
        return this.register(player.getVaro());
    }

    void setPlayer(VaroPlayer player);

    VaroPlayer getPlayer();

}