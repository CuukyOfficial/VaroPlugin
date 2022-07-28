package de.varoplugin.varo.game.entity.player;

import java.util.UUID;
import java.util.stream.Stream;

public interface PlayerContainer {

    VaroPlayer register(org.bukkit.entity.Player player);

    boolean remove(VaroPlayer player);

    VaroPlayer getPlayer(UUID uuid);

    VaroPlayer getPlayer(org.bukkit.entity.Player player);

    Stream<VaroPlayer> getPlayers();

}