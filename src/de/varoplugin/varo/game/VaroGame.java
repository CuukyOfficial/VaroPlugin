package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.api.event.game.VaroStateChangeEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerAddEvent;
import de.varoplugin.varo.game.entity.player.VaroGamePlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class VaroGame implements Varo {

    private VaroPlugin plugin;
    private VaroState state;

    private final Set<VaroPlayer> players;

    public VaroGame() {
        this.state = VaroGameState.LOBBY;
        this.players = new HashSet<>();
    }

    @Override
    public void initialize(VaroPlugin plugin) {
        this.plugin = plugin;
        this.registerTasks(this.state);

        for (Player player : this.getPlugin().getServer().getOnlinePlayers()) {
            VaroPlayer vp = this.getPlayer(player);
            if (vp == null) this.register(player);
            else vp.initialize(this);
        }
    }

    @Override
    public void registerTasks(VaroState state) {
        state.getTasks(this).forEach(CancelableTask::register);
    }

    @Override
    public VaroPlayer register(Player player) {
        VaroPlayer vp = new VaroGamePlayer(player);
        if (this.players.contains(vp) || this.plugin.isCancelled(new VaroPlayerAddEvent(vp))) return null;
        this.players.add(vp);
        vp.initialize(this);
        return vp;
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
    public Collection<VaroPlayer> getPlayers() {
        // TODO: Players
        return new ArrayList<>();
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