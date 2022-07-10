package de.varoplugin.varo.api.event.game.player;

import de.varoplugin.varo.game.entity.player.Player;
import org.bukkit.event.Cancellable;

public class VaroPlayerCancelableEvent extends VaroPlayerEvent implements Cancellable {

    private boolean cancelled;

    public VaroPlayerCancelableEvent(Player player) {
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