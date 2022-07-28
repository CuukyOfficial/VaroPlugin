package de.varoplugin.varo.task.player;

import de.varoplugin.varo.api.event.game.player.PlayerModeChangeEvent;
import de.varoplugin.varo.api.event.game.player.PlayerParticipantStateChangeEvent;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.session.EmptySessionBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.GregorianCalendar;

public class SessionTask extends AbstractPlayerListener {

    public SessionTask(VaroPlayer player) {
        super(player);
    }

    private void addSession() {
        this.getPlayer().addSession(new EmptySessionBuilder().start(new GregorianCalendar()).build());
    }

    @EventHandler
    public void onParticipantStateSwitch(PlayerParticipantStateChangeEvent event) {
        if (!event.getPlayer().equals(this.getPlayer())) return;
        if (this.getPlayer().getCurrentSession() == null) {
            if (event.getState().allowsSessions() && this.getPlayer().isOnline()) {
                this.addSession();
            }
        } else {
            if (!event.getState().allowsSessions()) this.getPlayer().endSession();
        }
    }

    @EventHandler
    public void onPlayerModSWitch(PlayerModeChangeEvent event) {
        if (!event.getPlayer().equals(this.getPlayer())) return;
        if (this.getPlayer().getCurrentSession() == null) {
            if (event.getMode().countsSessions() && this.getPlayer().isOnline()) {
                this.addSession();
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        if (this.getPlayer().getCurrentSession() != null) return;
        if (this.getPlayer().getMode().countsSessions() && this.getPlayer().getState().allowsSessions()) {
            this.addSession();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        if (this.getPlayer().getCountdown() != 0) return;
        if (this.getPlayer().getCurrentSession() == null) return;
        this.getPlayer().endSession();
    }
}
