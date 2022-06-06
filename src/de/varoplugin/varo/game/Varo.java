package de.varoplugin.varo.game;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface Varo {

    void initialize(VaroPlugin plugin);

    void registerTasks(VaroState state);

    VaroPlayer register(Player player);

    VaroPlayer getPlayer(UUID uuid);

    VaroPlayer getPlayer(Player player);

    Collection<VaroPlayer> getPlayers();

    VaroState getState();

    boolean setState(VaroState state);

    VaroPlugin getPlugin();

}