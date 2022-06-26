package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerAddEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.util.map.HashUniqueIdMap;
import de.varoplugin.varo.util.map.UniqueIdMap;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class GamePlayerContainer implements VaroPlayerContainer {

    private final UniqueIdMap<VaroPlayer> players;
    private final Varo varo;

    public GamePlayerContainer(Varo varo) {
        this.varo = varo;
        this.players = new HashUniqueIdMap<>();
    }

    @Override
    public VaroPlayer register(Player player) {
        VaroPlayer vp = new GamePlayer(player);
        if (this.players.contains(vp) || this.varo.getPlugin().isCancelled(new VaroPlayerAddEvent(vp))) return null;
        this.players.add(vp);
        vp.initialize(this.varo);
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
        return this.players.stream().collect(Collectors.toList());
    }

}