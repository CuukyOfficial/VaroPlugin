package de.varoplugin.varo.game.task.register;

import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.game.GameState;
import de.varoplugin.varo.game.task.KickNonRegisteredPlayerListener;
import de.varoplugin.varo.game.task.RegisterPlayerListener;
import de.varoplugin.varo.game.task.StartingTask;
import de.varoplugin.varo.game.task.player.NoMoveListener;
import de.varoplugin.varo.game.task.trigger.builder.VaroTriggerBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class DefaultTaskRegister implements Listener {

    private final Plugin plugin;

    public DefaultTaskRegister(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGameInitialize(VaroGameInitializedEvent event) {
        new VaroTriggerBuilder(this.plugin).or(GameState.LOBBY).complete(event.getVaro()).register(
                new RegisterPlayerListener(event.getVaro())
        );

        new VaroTriggerBuilder(this.plugin).orNot(GameState.LOBBY).complete(event.getVaro()).register(
                new KickNonRegisteredPlayerListener(event.getVaro())
        );

        new VaroTriggerBuilder(this.plugin).or(GameState.STARTING).complete(event.getVaro()).register(
                new StartingTask(event.getVaro())
        );
    }

    @EventHandler
    public void onPlayerInitialize(VaroPlayerInitializedEvent event) {
        new VaroTriggerBuilder(this.plugin).or(GameState.LOBBY).or(GameState.STARTING).complete(event.getVaro()).register(
                new NoMoveListener(event.getPlayer())
        );
//        new VaroTriggerBuilder(this.plugin).or(GameState.STARTING).and(
//                new VaroPlayerTriggerBuilder(this.plugin).or(true).build(event.getPlayer())
//        ).complete(event.getVaro()).register(new SpamPlayerTask(event.getPlayer()));
    }
}
