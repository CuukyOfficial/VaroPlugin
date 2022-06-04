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
public class AliveEndInfo extends AbstractPlayerInfo {

    public AliveEndInfo() {
        super(VaroGameState.FINISHED);
    }


    @Override
    public List<CancelableListener> getListener(VaroPlayer player) {
        // TODO: Add end listener
        return null;
    }

    @Override
    public GameMode getGameMode() {
        // TODO: Customizable
        return GameMode.SURVIVAL;
    }
}
