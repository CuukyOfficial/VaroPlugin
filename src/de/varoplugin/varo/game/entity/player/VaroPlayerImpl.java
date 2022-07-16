package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.api.event.game.player.*;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.VaroEntityImpl;
import de.varoplugin.varo.game.entity.player.session.Session;
import de.varoplugin.varo.game.entity.team.Team;
import de.varoplugin.varo.game.strike.Strike;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Stream;

final class VaroPlayerImpl extends VaroEntityImpl implements VaroPlayer {

    private ParticipantState state;
    private PlayerMode mode;
    private Team team;
    private Player player;
    private String name;

    private int countdown;
    private int kills;
    private Collection<Session> sessions;

    VaroPlayerImpl(UUID uuid, String name, Player player, ParticipantState state, PlayerMode mode) {
        super(uuid);
        this.name = name;
        this.player = player;
        this.state = state;
        this.mode = mode;
        this.sessions = new HashSet<>();
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new PlayerInitializedEvent(this));
    }

    @Override
    public boolean strike(Strike strike) {
        strike.setTarget(this);
        // TODO: Implement
        return false;
    }

    @Override
    public void kill() {
        // TODO: Implement
    }

    @Override
    public void setCountdown(int countdown) {
        if (this.countdown == countdown) return;
        PlayerCountdownChangeEvent event = new PlayerCountdownChangeEvent(this, countdown);
        if (this.getVaro().getPlugin().isCancelled(event)) return;
        this.countdown = event.getCountdown();
    }

    @Override
    public int getCountdown() {
        return this.countdown;
    }

    @Override
    public boolean isOnline() {
        return this.player != null;
    }

    @Override
    public boolean clearInventory() {
        // TODO: Implement
        return false;
    }

    @Override
    public Location getLocation() {
        // TODO: Implement
        return null;
    }

    @Override
    public boolean isAlive() {
        return this.state == VaroParticipantState.ALIVE;
    }

    @Override
    public boolean isPlayer(Player player) {
        return player.getUniqueId().equals(this.getUuid());
    }

    @Override
    public boolean setState(ParticipantState state) {
        if (this.state == state) return false;
        PlayerParticipantStateChangeEvent event = new PlayerParticipantStateChangeEvent(this, state);
        if (this.getVaro().getPlugin().isCancelled(event)) return false;
        this.state = event.getState();
        return true;
    }

    @Override
    public ParticipantState getState() {
        return this.state;
    }

    @Override
    public boolean setMode(PlayerMode mode) {
        if (this.mode == mode) return false;
        PlayerModeChangeEvent event = new PlayerModeChangeEvent(this, mode);
        if (this.getVaro().getPlugin().isCancelled(event)) return false;
        this.mode = event.getMode();
        return true;
    }

    @Override
    public PlayerMode getMode() {
        return this.mode;
    }

    @Override
    public void setPlayer(org.bukkit.entity.Player player) {
        this.player = player;
    }

    @Override
    public void addSession(Session session) {
        this.sessions.add(session);
    }

    @Override
    public Stream<Session> getSessions() {
        return this.sessions.stream();
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getKills() {
        return this.kills;
    }

    @Override
    public boolean setKills(int kills) {
        if (this.kills == kills) return false;
        PlayerKillsChangeEvent event = new PlayerKillsChangeEvent(this, kills);
        if (this.getVaro().getPlugin().isCancelled(event)) return false;
        this.kills = event.getKills();
        return true;
    }

    @Override
    public boolean hasTeam() {
        return this.team != null;
    }

    @Override
    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }
}
