package de.varoplugin.varo.game.player.state;

import de.varoplugin.varo.game.CancelableListener;
import de.varoplugin.varo.game.VaroGameState;
import de.varoplugin.varo.game.player.VaroPlayer;
import org.bukkit.GameMode;

import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class AliveRunningInfo extends AbstractPlayerInfo {

    public AliveRunningInfo() {
        super(VaroGameState.RUNNING);
    }

    @Override
    public List<CancelableListener> getListener(VaroPlayer player) {
        // TODO: Add running listener
        return null;
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.SURVIVAL;
    }
}