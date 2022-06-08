package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.Cancellable;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroPlayerCancelableEvent extends VaroPlayerEvent implements Cancellable {

    private boolean cancelled;

    public VaroPlayerCancelableEvent(VaroPlayer player) {
        super(player);
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}