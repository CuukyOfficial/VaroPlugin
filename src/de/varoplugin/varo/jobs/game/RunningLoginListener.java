package de.varoplugin.varo.jobs.game;

import de.varoplugin.varo.api.event.game.VaroGameLoginKickEvent;
import de.varoplugin.varo.game.GameKickResult;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobs.AbstractVaroListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class RunningLoginListener extends AbstractVaroListener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        VaroPlayer player = this.getVaro().getPlayer(event.getPlayer());
        if (player != null) return;

        VaroGameLoginKickEvent loginKickEvent = new VaroGameLoginKickEvent(this.getVaro(), event, GameKickResult.NOT_A_PARTICIPANT);
        if (!this.getVaro().getPlugin().isCancelled(loginKickEvent)) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}