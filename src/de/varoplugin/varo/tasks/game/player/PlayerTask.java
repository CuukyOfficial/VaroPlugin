package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.AbstractVaroTask;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class PlayerTask extends AbstractVaroTask implements VaroPlayerTask {

    private VaroPlayer player;

    protected boolean shallIgnore(PlayerEvent event) {
        return !event.getPlayer().equals(this.player.getPlayer());
    }

    @Override
    public void setPlayer(VaroPlayer player) {
        this.player = player;
    }

    @Override
    public VaroPlayer getPlayer() {
        return this.player;
    }
}