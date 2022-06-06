package de.varoplugin.varo.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerStateChangeEvent;
import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.Varo;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroGamePlayer implements VaroPlayer {

    private Varo varo;
    private UUID uuid;
    private VaroPlayerState state;

    // TODO: Stats
    // Stats
    private int kills;

    private Player player;

    /**
     * For Serialization. Do not use.
     */
    private VaroGamePlayer() {
        this.state = VaroGamePlayerState.ALIVE;
    }

    public VaroGamePlayer(UUID uuid) {
        this();
        this.uuid = uuid;
    }

    public VaroGamePlayer(Player player) {
        this(player.getUniqueId());
        this.player = player;
    }

    @Override
    public void initialize(Varo varo) {
        this.varo = varo;
        this.registerListener(this.state);
    }

    @Override
    public void registerListener(VaroPlayerState state) {
        state.getInfo(this.varo.getState()).getTasks(this).forEach(CancelableTask::register);
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
    public boolean setState(VaroPlayerState state) {
        if (this.state == state || this.varo.getPlugin().isCancelled(new VaroPlayerStateChangeEvent(this, state)))
            return false;
        this.state = state;
        return true;
    }

    @Override
    public VaroPlayerState getState() {
        return this.state;
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
