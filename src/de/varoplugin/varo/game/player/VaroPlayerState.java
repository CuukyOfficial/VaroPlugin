package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.player.login.VaroLoginResult;
import org.bukkit.GameMode;

public interface VaroPlayerState {

    VaroLoginResult loggedIn(VaroPlayer player);

    GameMode getGameMode();

}