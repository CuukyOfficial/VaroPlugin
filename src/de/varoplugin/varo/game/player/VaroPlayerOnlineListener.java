package de.varoplugin.varo.game.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerOnlineListener extends VaroPlayerStateListener {

    public VaroPlayerOnlineListener(VaroPlayer player) {
        super(player);
    }

    @Override
    public boolean shallListen() {
        return super.shallListen() && this.player.isOnline();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (this.player.getPlayer().equals(event.getPlayer())) this.unregister();
    }
}