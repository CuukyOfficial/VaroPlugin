package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.game.entity.player.GamePlayerContainer;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerContainer;
import de.varoplugin.varo.game.entity.team.VaroTeam;
import de.varoplugin.varo.util.map.HashUniqueIdMap;
import de.varoplugin.varo.util.map.UniqueIdMap;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public class Game implements Varo {

    private VaroPlugin plugin;
    private VaroState state;

    private final VaroPlayerContainer players;
    private final UniqueIdMap<VaroTeam> teams;

    public Game() {
        this.state = GameState.LOBBY;
        this.players = new GamePlayerContainer(this);
        this.teams = new HashUniqueIdMap<>();
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
    public boolean addTeam(VaroTeam team) {
        team.initialize(this);
        return this.teams.add(team);
    }

    @Override
    public VaroPlugin getPlugin() {
        return this.plugin;
    }

}