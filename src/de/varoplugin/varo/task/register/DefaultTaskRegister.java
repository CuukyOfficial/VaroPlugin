package de.varoplugin.varo.task.register;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.task.builder.VaroTriggerBuilder;
import de.varoplugin.varo.task.game.KickNonRegisteredPlayerListener;
import de.varoplugin.varo.task.game.RegisterPlayerListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class DefaultTaskRegister implements Listener {

    @EventHandler
    public void onGameInitialize(VaroGameInitializedEvent event) {
        new VaroTriggerBuilder().or(GameState.LOBBY).complete(event.getVaro()).register(
                new RegisterPlayerListener(event.getVaro())
        );

        new VaroTriggerBuilder().orNot(GameState.LOBBY).complete(event.getVaro()).register(
                new KickNonRegisteredPlayerListener(event.getVaro())
        );
    }

    @EventHandler
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {

    }
}