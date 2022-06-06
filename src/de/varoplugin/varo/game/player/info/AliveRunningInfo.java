package de.varoplugin.varo.game.player.info;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.VaroGameState;
import de.varoplugin.varo.game.player.VaroPlayer;
import de.varoplugin.varo.game.player.tasks.CountdownTask;
import org.bukkit.GameMode;

import java.util.Arrays;
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
    public List<CancelableTask> getTasks(VaroPlayer player) {
        // TODO: Add running listener
        return Arrays.asList(new CountdownTask(player));
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.SURVIVAL;
    }
}