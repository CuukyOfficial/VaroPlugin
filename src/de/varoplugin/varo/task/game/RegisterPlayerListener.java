package de.varoplugin.varo.task.game;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.task.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class RegisterPlayerListener extends AbstractListener {

    public RegisterPlayerListener(Varo varo) {
        super(varo);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLobbyLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        // TODO: Add register before start config option and make default player state configurable
        this.getVaro().register(event.getPlayer());
    }
}