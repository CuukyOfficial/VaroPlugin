package de.varoplugin.varo.game;

import de.cuuky.cfw.version.VersionUtils;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.event.game.VaroGameInitializedEvent;
import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerAddEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.entity.player.GamePlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.VaroTeam;
import de.varoplugin.varo.util.map.HashUniqueIdMap;
import de.varoplugin.varo.util.map.UniqueIdMap;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Stream;

public class Game implements Varo {

    private VaroPlugin plugin;
    private VaroState state;

    private UniqueIdMap<VaroTeam> teams;
    private UniqueIdMap<VaroPlayer> players;

    public Game() {
        this.state = GameState.LOBBY;
    }

    @Override
    public void initialize(VaroPlugin plugin) {
        this.plugin = plugin;
        if (this.players == null) this.players = new HashUniqueIdMap<>();
        if (this.teams == null) this.teams = new HashUniqueIdMap<>();

        for (Player player : VersionUtils.getVersionAdapter().getOnlinePlayers()) {
            VaroPlayer vp = this.getPlayer(player);
            if (vp == null) this.register(player);
            else vp.initialize(this);
        }
        this.plugin.callEvent(new VaroGameInitializedEvent(this));
    }

    @Override
    public VaroPlayer register(Player player) {
        VaroPlayer vp = new GamePlayer(player);
        if (this.players.contains(vp) || this.plugin.isCancelled(new VaroPlayerAddEvent(this, vp))) return null;
        this.players.add(vp);
        vp.initialize(this);
        return vp;
    }

    @Override
    public boolean remove(VaroPlayer player) {
        if (!this.players.contains(player) || this.plugin.isCancelled(new VaroPlayerRemoveEvent(this, player))) return false;
        return this.players.remove(player);
    }

    @Override
    public VaroPlayer getPlayer(UUID uuid) {
        return this.players.stream().filter(player -> player.getUuid().equals(uuid)).findAny().orElse(null);
    }

    @Override
    public VaroPlayer getPlayer(Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    @Override
    public Stream<VaroPlayer> getPlayers() {
        return this.players.stream();
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
    public Stream<VaroTeam> getTeams() {
        return this.teams.stream();
    }

    @Override
    public VaroPlugin getPlugin() {
        return this.plugin;
    }

}