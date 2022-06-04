package de.varoplugin.varo.game.player.login;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.player.VaroPlayer;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.function.BiFunction;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class LoginKick implements ResultApplier {

    private final String message;
    private final BiFunction<Varo, VaroPlayer, Object[]> formatObjects;

    public LoginKick(String message, BiFunction<Varo, VaroPlayer, Object[]> formatObjects) {
        this.message = message;
        this.formatObjects = formatObjects;
    }

    @Override
    public void apply(Varo varo, PlayerLoginEvent event, VaroPlayer player) {
        Object[] objects = this.formatObjects.apply(varo, player);
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, String.format(this.message, objects));
    }
}