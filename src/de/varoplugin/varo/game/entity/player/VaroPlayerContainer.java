package de.varoplugin.varo.game.entity.player;

import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Stream;

public interface VaroPlayerContainer {

    VaroPlayer register(Player player);

    boolean remove(VaroPlayer player);

    VaroPlayer getPlayer(UUID uuid);

    VaroPlayer getPlayer(Player player);

    Stream<VaroPlayer> getPlayers();

}