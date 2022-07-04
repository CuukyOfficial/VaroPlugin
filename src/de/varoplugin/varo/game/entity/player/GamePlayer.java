package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerInitializedEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerKillsChangeEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerModeChangeEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerParticipantStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.GameEntity;
import de.varoplugin.varo.game.entity.VaroEntity;
import de.varoplugin.varo.game.entity.team.VaroTeam;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer extends GameEntity implements VaroPlayer {

    private VaroParticipantState state;
    private VaroPlayerMode mode;

    private int kills;

    private Player player;

    /**
     * For Serialization. Do not use.
     */
    private GamePlayer() {
        super(null);
    }

    public GamePlayer(UUID uuid) {
        super(uuid);
        this.state = ParticipantState.ALIVE;
        this.mode = PlayerMode.NONE;
    }

    public GamePlayer(Player player) {
        this(player.getUniqueId());
        this.player = player;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new VaroPlayerInitializedEvent(this));
    }

    @Override
    public boolean canAccessSavings(VaroEntity player) {
        return this.getUuid().equals(player.getUuid());
    }

    @Override
    public boolean isOnline() {
        return this.player != null;
    }

    @Override
    public boolean isPlayer(Player player) {
        return player.getUniqueId().equals(this.getUuid());
    }

    @Override
    public boolean setState(VaroParticipantState state) {
        if (this.state == state || this.getVaro().getPlugin().isCancelled(new VaroPlayerParticipantStateChangeEvent(this, state)))
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
        if (this.mode == mode || this.getVaro().getPlugin().isCancelled(new VaroPlayerModeChangeEvent(this, mode)))
            return false;
        this.mode = mode;
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
    public int getKills() {
        return this.kills;
    }

    @Override
    public boolean setKills(int kills) {
        if (this.kills == kills || !this.getVaro().getPlugin().isCancelled(new VaroPlayerKillsChangeEvent(this, kills))) return false;
        this.kills = kills;
        return true;
    }

    @Override
    public VaroTeam getTeam() {
        return null;
    }
}
