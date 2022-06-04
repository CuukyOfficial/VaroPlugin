package de.varoplugin.varo.game.player.state;

import de.varoplugin.varo.game.CancelableListener;
import de.varoplugin.varo.game.VaroGameState;
import de.varoplugin.varo.game.player.VaroPlayer;
import de.varoplugin.varo.game.player.state.listener.NoMoveListener;
import org.bukkit.GameMode;

import java.util.Arrays;
import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class AliveLobbyInfo extends AbstractPlayerInfo {

    public AliveLobbyInfo() {
        super(VaroGameState.LOBBY);
    }

    @Override
    public List<CancelableListener> getListener(VaroPlayer player) {
        return Arrays.asList(new NoMoveListener(player));
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.ADVENTURE;
    }
}
