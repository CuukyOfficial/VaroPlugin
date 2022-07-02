package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.entity.player.GamePlayerContainer;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerContainer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class Game implements Varo {

    private VaroPlugin plugin;
    private VaroState state;

    private final VaroPlayerContainer players;

    public Game() {
        this.state = GameState.LOBBY;
        this.players = new GamePlayerContainer(this);
    }

    @Override
    public void initialize(VaroPlugin plugin) {
        this.plugin = plugin;

        for (Player player : this.getPlugin().getServer().getOnlinePlayers()) {
            VaroPlayer vp = this.getPlayer(player);
            if (vp == null) this.register(player);
            else vp.initialize(this);
        }
        this.plugin.callEvent(new VaroGameInitializedEvent(this));
    }

    @Override
    public VaroPlayer register(Player player) {
        return this.players.register(player);
    }

    @Override
    public VaroPlayer getPlayer(UUID uuid) {
        return this.players.getPlayer(uuid);
    }

    @Override
    public VaroPlayer getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    @Override
    public Collection<VaroPlayer> getPlayers() {
        return this.players.getPlayers();
    }

    @Override
    public VaroState getState() {
        return this.state;
    }

    @Override
    public boolean setState(VaroState state) {
        if (this.state == state || this.plugin.isCancelled(new VaroStateChangeEvent(this, state))) return false;
        this.state = state;
        return true;
    }

    @Override
    public VaroPlugin getPlugin() {
        return this.plugin;
    }

}