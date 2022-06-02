package de.varoplugin.varo.game.player.login;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.player.VaroPlayer;
import org.bukkit.event.player.PlayerLoginEvent;

public interface VaroLoginResult {

    void apply(Varo varo, PlayerLoginEvent event, VaroPlayer player);

}