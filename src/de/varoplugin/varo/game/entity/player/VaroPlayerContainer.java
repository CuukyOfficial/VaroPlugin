package de.varoplugin.varo.game.entity.player;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerContainer {

    VaroPlayer register(Player player);

    VaroPlayer getPlayer(UUID uuid);

    VaroPlayer getPlayer(Player player);

    Collection<VaroPlayer> getPlayers();

}