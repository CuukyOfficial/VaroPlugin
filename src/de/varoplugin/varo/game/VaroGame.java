package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.event.game.GameStateChangeEvent;
import de.varoplugin.varo.game.heartbeat.Heartbeat;
import de.varoplugin.varo.game.player.VaroPlayer;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroGame implements Varo {

    private VaroPlugin plugin;
    private VaroState state;

    private Heartbeat heartbeat;

    public VaroGame() {
        this.state = VaroGameState.LOBBY;
    }

    private void loadHeartbeat() {
        if (this.heartbeat != null) this.heartbeat.cancel();
        this.heartbeat = this.state.createHeartbeat();
        this.heartbeat.initialize(this);
    }

    @Override
    public void initialize(VaroPlugin plugin) {
        this.plugin = plugin;
        this.loadHeartbeat();
    }

    @Override
    public boolean register(VaroPlayer player) {
        return false;
    }

    @Override
    public VaroState getState() {
        return this.state;
    }

    @Override
    public boolean setState(VaroState state) {
        GameStateChangeEvent event = new GameStateChangeEvent(this, state);
        if (this.plugin.callEvent(event).isCancelled()) return false;
        this.state = state;
        this.loadHeartbeat();
        return true;
    }

    @Override
    public VaroPlugin getPlugin() {
        return this.plugin;
    }
}