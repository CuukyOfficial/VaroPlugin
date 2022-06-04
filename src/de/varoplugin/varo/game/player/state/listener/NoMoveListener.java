package de.varoplugin.varo.game.player.state.listener;

import de.varoplugin.varo.game.player.VaroPlayer;
import de.varoplugin.varo.game.player.VaroPlayerOnlineListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class NoMoveListener extends VaroPlayerOnlineListener {

    public NoMoveListener(VaroPlayer player) {
        super(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        event.setCancelled(true);
    }
}