package de.varoplugin.varo.game.player.info;

import de.varoplugin.varo.game.CancelableTask;
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
    public List<CancelableTask> getTasks(VaroPlayer player) {
        // TODO: Add end listener
        return null;
    }

    @Override
    public GameMode getGameMode() {
        // TODO: Customizable
        return GameMode.SURVIVAL;
    }
}
