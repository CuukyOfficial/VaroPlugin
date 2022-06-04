package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.Varo;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayer {

    boolean isOnline();

    Varo getVaro();

    UUID getUuid();

    boolean setState(VaroPlayerState state);

    VaroPlayerState getState();

    Player getPlayer();

}