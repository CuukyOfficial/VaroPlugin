package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.AbstractVaroTask;
import de.varoplugin.varo.tasks.register.VaroRegisterInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class RunningLoginTask extends AbstractVaroTask<VaroRegisterInfo> {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        VaroPlayer player = this.getInfo().getVaro().getPlayer(event.getPlayer());
        if (player != null) return;
        // TODO: Let the UI set the kick message
        event.setKickMessage("Nah men not now");
        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
    }
}