package de.varoplugin.varo.tasks.register;

import de.varoplugin.varo.game.entity.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class PlayerTaskInfo extends RegisterInfo implements VaroPlayerTaskInfo {

    private final VaroPlayer player;

    public PlayerTaskInfo(VaroPlayer player) {
        super(player.getVaro());

        this.player = player;
    }

    @Override
    public VaroPlayer getPlayer() {
        return this.player;
    }
}