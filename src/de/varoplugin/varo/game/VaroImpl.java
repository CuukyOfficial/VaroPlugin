package de.varoplugin.varo.game;

import de.cuuky.cfw.version.VersionUtils;
import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.event.game.VaroAutoStartChangedEvent;
import de.varoplugin.varo.api.event.game.VaroInitializedEvent;
import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.api.event.game.player.PlayerAddEvent;
import de.varoplugin.varo.api.event.game.player.PlayerRemoveEvent;
import de.varoplugin.varo.game.entity.player.EmptyPlayerBuilder;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.Team;
import de.varoplugin.varo.game.entity.team.Teamable;
import de.varoplugin.varo.util.map.HashUniqueIdMap;
import de.varoplugin.varo.util.map.UniqueIdMap;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

final class VaroImpl implements Varo {

    private VaroPlugin plugin;
    private State current;
    private Calendar autoStart;

    private final UniqueIdMap<Team> teams;
    private final UniqueIdMap<VaroPlayer> players;
    private final Set<Location> itemChestLocations;
    private final Collection<State> availableStates;

    VaroImpl(Collection<State> availableStates) {
        this.availableStates = availableStates;
        this.teams = new HashUniqueIdMap<>();
        this.players = new HashUniqueIdMap<>();
        this.itemChestLocations = new HashSet<>();
    }

    private Optional<State> optionalState(int start) {
        return this.availableStates.stream().filter(i -> i.getPriority() > start)
                .min(Comparator.comparingInt(State::getPriority));
    }

    private State getFirstState() {
        // TODO: Own exception
        return this.optionalState(Integer.MIN_VALUE).orElseThrow(NullPointerException::new);
    }

    private State getNextState(int start) {
        return this.optionalState(start).orElse(this.getFirstState());
    }

    @Override
    public void initialize(VaroPlugin plugin) {
        this.plugin = plugin;
        this.current = this.current == null ? this.getFirstState() : this.current;

        for (Player player : VersionUtils.getVersionAdapter().getOnlinePlayers()) {
            VaroPlayer vp = this.getPlayer(player);
            if (vp == null) this.register(player);
            else vp.initialize(this);
        }
        this.plugin.callEvent(new VaroInitializedEvent(this));
    }

    @Override
    public VaroPlayer register(Player player) {
        VaroPlayer vp = new EmptyPlayerBuilder().player(player).create();
        if (this.players.contains(vp) || this.plugin.isCancelled(new PlayerAddEvent(this, vp))) return null;
        this.players.add(vp);
        vp.initialize(this);
        return vp;
    }

    @Override
    public boolean remove(VaroPlayer player) {
        if (!this.players.contains(player) || this.plugin.isCancelled(new PlayerRemoveEvent(this, player))) return false;
        return this.players.remove(player);
    }

    @Override
    public VaroPlayer getPlayer(UUID uuid) {
        return this.players.stream().filter(player -> player.getUuid().equals(uuid)).findAny().orElse(null);
    }

    @Override
    public VaroPlayer getPlayer(org.bukkit.entity.Player player) {
        return this.getPlayer(player.getUniqueId());
    }

    @Override
    public Stream<VaroPlayer> getPlayers() {
        return this.players.stream();
    }

    @Override
    public State getState() {
        return this.current;
    }

    @Override
    public void nextState() {
        this.current = this.getNextState(this.current.getPriority());
    }

    @Override
    public boolean setState(State state) {
        if (this.current == state || this.plugin.isCancelled(new VaroStateChangeEvent(this, state))) return false;
        this.current = state;
        return true;
    }

    @Override
    public Stream<State> getStates() {
        return this.availableStates.stream();
    }

    @Override
    public boolean addTeamMember(Team to, Teamable member) {
        if (to.addMember(member)) {
            member.setTeam(to);
            return true;
        }
        return false;
    }

    @Override
    public boolean addTeam(Team team) {
        team.initialize(this);
        return this.teams.add(team);
    }

    @Override
    public Stream<Team> getTeams() {
        return this.teams.stream();
    }

    @Override
    public VaroPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public boolean setAutoStart(Calendar calendar) {
        if (Objects.equals(calendar, this.autoStart)) return false;
        this.autoStart = calendar;
        this.plugin.callEvent(new VaroAutoStartChangedEvent(this, calendar));
        return true;
    }

    @Override
    public Calendar getAutoStart() {
        return this.autoStart;
    }

    @Override
    public void addItemChests(Collection<Block> location) {
        location.stream().map(Block::getLocation).forEach(this.itemChestLocations::add);
    }

    @Override
    public Stream<Location> getItemChestLocations() {
        return this.itemChestLocations.stream();
    }
}