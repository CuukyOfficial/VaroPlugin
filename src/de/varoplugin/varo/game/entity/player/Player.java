package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerKillsChangeEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerModeChangeEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerParticipantStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.GameEntity;

import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class Player extends GameEntity implements VaroPlayer {

    private UUID uuid;
    private VaroParticipantState state;
    private VaroPlayerMode mode;

    private int kills;

    private org.bukkit.entity.Player player;

    /**
     * For Serialization. Do not use.
     */
    private Player() {
        this.state = ParticipantState.ALIVE;
        this.mode = PlayerMode.NONE;
    }

    public Player(UUID uuid) {
        this();
        this.uuid = uuid;
    }

    public Player(org.bukkit.entity.Player player) {
        this(player.getUniqueId());
        this.player = player;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        this.getVaro().getPlugin().callEvent(new VaroPlayerInitializedEvent(this));
    }

    @Override
    public boolean canAccessSavings(VaroPlayer player) {
        return this.equals(player);
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }

    @Override
    public boolean isOnline() {
        return this.player != null;
    }

    @Override
    public Varo getVaro() {
        return this.varo;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public boolean setState(VaroParticipantState state) {
        if (this.state == state || this.varo.getPlugin().isCancelled(new VaroPlayerParticipantStateChangeEvent(this, state)))
            return false;
        this.state = state;
        return true;
    }

    @Override
    public VaroParticipantState getState() {
        return this.state;
    }

    @Override
    public boolean setMode(VaroPlayerMode mode) {
        if (this.mode == mode || this.varo.getPlugin().isCancelled(new VaroPlayerModeChangeEvent(this, mode)))
            return false;
        this.mode = mode;
        return true;
    }

    @Override
    public VaroPlayerMode getMode() {
        return this.mode;
    }

    @Override
    public void setPlayer(org.bukkit.entity.Player player) {
        this.player = player;
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return this.player;
    }

    @Override
    public int getKills() {
        return this.kills;
    }

    @Override
    public boolean setKills(int kills) {
        if (!this.getVaro().getPlugin().isCancelled(new VaroPlayerKillsChangeEvent(this, kills))) return false;
        this.kills = kills;
        return true;
    }
}
