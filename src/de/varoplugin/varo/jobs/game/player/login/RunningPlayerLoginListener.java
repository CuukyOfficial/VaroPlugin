package de.varoplugin.varo.jobs.game.player.login;

import de.varoplugin.varo.api.event.game.player.VaroPlayerLoginEvent;
import de.varoplugin.varo.game.entity.player.GamePlayerKickResult;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerKickResult;
import de.varoplugin.varo.jobs.AbstractVaroListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public abstract class RunningPlayerLoginListener extends AbstractVaroListener {

    private final VaroPlayer player;

    public RunningPlayerLoginListener(VaroPlayer player) {
        this.player = player;
    }

    protected abstract VaroPlayerKickResult onVaroPlayerLogin(PlayerLoginEvent event);

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED || !this.player.isPlayer(event.getPlayer())) return;
        VaroPlayerKickResult result = this.onVaroPlayerLogin(event);

        VaroPlayerLoginEvent loginEvent = new VaroPlayerLoginEvent(this.player, event, result);
        this.getVaro().getPlugin().callEvent(loginEvent);
        if (loginEvent.getResult() != GamePlayerKickResult.ALLOWED) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    public VaroPlayer getPlayer() {
        return this.player;
    }
}
