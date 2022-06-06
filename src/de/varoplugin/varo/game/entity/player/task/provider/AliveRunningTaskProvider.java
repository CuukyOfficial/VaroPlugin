package de.varoplugin.varo.game.entity.player.task.provider;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.task.CountdownTask;
import org.bukkit.GameMode;

import java.util.Collections;
import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class AliveRunningTaskProvider implements VaroPlayerStateTaskProvider {

    @Override
    public List<CancelableTask> getTasks(VaroPlayer player) {
        // TODO: Add running listener
        return Collections.singletonList(new CountdownTask(player));
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.SURVIVAL;
    }
}