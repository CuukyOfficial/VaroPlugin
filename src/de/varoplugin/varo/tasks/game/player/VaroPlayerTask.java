package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.AbstractVaroTask;
import org.bukkit.event.player.PlayerEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerTask extends AbstractVaroTask {

    protected final VaroPlayer player;

    public VaroPlayerTask(VaroPlayer player) {
        this.player = player;
    }

    protected boolean shallIgnore(PlayerEvent event) {
        return !event.getPlayer().equals(player.getPlayer());
    }
}