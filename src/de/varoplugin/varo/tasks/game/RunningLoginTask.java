package de.varoplugin.varo.tasks.game;

import de.varoplugin.varo.api.event.game.VaroGameLoginKickEvent;
import de.varoplugin.varo.game.KickResult;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.AbstractVaroTask;
import de.varoplugin.varo.tasks.register.VaroRegisterInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class RunningLoginTask extends AbstractVaroTask<VaroRegisterInfo> {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        VaroPlayer player = this.getInfo().getVaro().getPlayer(event.getPlayer());
        if (player != null) return;

        VaroGameLoginKickEvent loginKickEvent = new VaroGameLoginKickEvent(this.getInfo().getVaro(), event, KickResult.NOT_A_PARTICIPANT);
        if (!this.getInfo().getVaro().getPlugin().isCancelled(loginKickEvent)) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}