package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.api.event.game.player.*;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.VaroEntityImpl;
import de.varoplugin.varo.game.entity.team.VaroTeam;
import de.varoplugin.varo.game.strike.Strike;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VaroPlayerImpl extends VaroEntityImpl implements VaroPlayer {

    private VaroParticipantState state;
    private VaroPlayerMode mode;
    private VaroTeam team;

    private int countdown;
    private int kills;

    private Player player;
    private String name;

    /**
     * For Serialization. Do not use.
     */
    private VaroPlayerImpl() {
        super(null);
    }

    public VaroPlayerImpl(UUID uuid) {
        super(uuid);
        this.state = ParticipantState.ALIVE;
        this.mode = PlayerMode.NONE;
    }

    public VaroPlayerImpl(Player player) {
        this(player.getUniqueId());
        this.player = player;
        this.name = player.getName();
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new VaroPlayerInitializedEvent(this));
    }

    @Override
    public boolean strike(Strike strike) {
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
        VaroPlayerCountdownChangeEvent event = new VaroPlayerCountdownChangeEvent(this, countdown);
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
        return this.state == ParticipantState.ALIVE;
    }

    @Override
    public boolean isPlayer(Player player) {
        return player.getUniqueId().equals(this.getUuid());
    }

    @Override
    public boolean setState(VaroParticipantState state) {
        if (this.state == state) return false;
        VaroPlayerParticipantStateChangeEvent event = new VaroPlayerParticipantStateChangeEvent(this, state);
        if (this.getVaro().getPlugin().isCancelled(event)) return false;
        this.state = event.getState();
        return true;
    }

    @Override
    public VaroParticipantState getState() {
        return this.state;
    }

    @Override
    public boolean setMode(VaroPlayerMode mode) {
        if (this.mode == mode) return false;
        VaroPlayerModeChangeEvent event = new VaroPlayerModeChangeEvent(this, mode);
        if (this.getVaro().getPlugin().isCancelled(event)) return false;
        this.mode = event.getMode();
        return true;
    }

    @Override
    public VaroPlayerMode getMode() {
        return this.mode;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
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
        VaroPlayerKillsChangeEvent event = new VaroPlayerKillsChangeEvent(this, kills);
        if (this.getVaro().getPlugin().isCancelled(event)) return false;
        this.kills = event.getKills();
        return true;
    }

    @Override
    public boolean hasTeam() {
        return this.team != null;
    }

    @Override
    public void setTeam(VaroTeam team) {
        this.team = team;
    }

    @Override
    public VaroTeam getTeam() {
        return this.team;
    }
}
