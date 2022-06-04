package de.varoplugin.varo.game.player.listener;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.player.VaroPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public abstract class VaroPlayerOnlineListener extends VaroPlayerStateListener {

    public VaroPlayerOnlineListener(Varo varo, VaroState state, VaroPlayer player) {
        super(varo, state, player);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (this.player.getPlayer().equals(event.getPlayer())) this.unregister();
    }
}