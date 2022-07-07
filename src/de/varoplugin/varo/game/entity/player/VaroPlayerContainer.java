package de.varoplugin.varo.game.entity.player;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

public interface VaroPlayerContainer {

    VaroPlayer register(Player player);

    boolean remove(VaroPlayer player);

    VaroPlayer getPlayer(UUID uuid);

    VaroPlayer getPlayer(Player player);

    /**
     * Returns a copied version of the varo player collection.
     *
     * @return A collection containing all players
     */
    Collection<VaroPlayer> getPlayers();

}