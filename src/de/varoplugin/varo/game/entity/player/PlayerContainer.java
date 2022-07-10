package de.varoplugin.varo.game.entity.player;

import java.util.UUID;
import java.util.stream.Stream;

public interface PlayerContainer {

    Player register(org.bukkit.entity.Player player);

    boolean remove(Player player);

    Player getPlayer(UUID uuid);

    Player getPlayer(org.bukkit.entity.Player player);

    Stream<Player> getPlayers();

}