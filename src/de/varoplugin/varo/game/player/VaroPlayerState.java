package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.player.login.VaroLoginResult;
import org.bukkit.GameMode;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerState {

    VaroLoginResult loggedIn(VaroPlayer player);

    GameMode getGameMode();

}