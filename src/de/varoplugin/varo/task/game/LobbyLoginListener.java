package de.varoplugin.varo.task.game;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.task.AbstractListener;
import de.varoplugin.varo.task.VaroRegistrable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class LobbyLoginListener extends AbstractListener {

    public LobbyLoginListener(Varo varo) {
        super(varo);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLobbyLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;

        // TODO: Add register before start config option and make default player state configurable
        this.getVaro().register(event.getPlayer());
    }

    @Override
    public VaroRegistrable deepClone() {
        return new LobbyLoginListener(this.getVaro());
    }
}