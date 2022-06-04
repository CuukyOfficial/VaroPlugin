package de.varoplugin.varo.game.player;

import de.varoplugin.varo.game.player.login.AlivePlayerCheck;
import de.varoplugin.varo.game.player.login.DefaultLoginResult;
import de.varoplugin.varo.game.player.login.VaroLoginResult;
import org.bukkit.GameMode;

import java.util.function.Function;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultPlayerState implements VaroPlayerState {

    ALIVE(GameMode.SURVIVAL, new AlivePlayerCheck()),
    SPECTATOR(GameMode.ADVENTURE, (p) -> DefaultLoginResult.ALLOWED),
    DEAD(null, (p) -> DefaultLoginResult.PLAYER_DEAD);

    private final GameMode gameMode;
    private final Function<VaroPlayer, VaroLoginResult> loginResult;

    DefaultPlayerState(GameMode gameMode, Function<VaroPlayer, VaroLoginResult> loginResult) {
        this.gameMode = gameMode;
        this.loginResult = loginResult;
    }

    @Override
    public VaroLoginResult loggedIn(VaroPlayer player) {
        return this.loginResult.apply(player);
    }

    @Override
    public GameMode getGameMode() {
        return this.gameMode;
    }
}