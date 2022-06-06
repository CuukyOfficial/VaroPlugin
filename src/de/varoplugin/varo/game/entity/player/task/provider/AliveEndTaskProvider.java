package de.varoplugin.varo.game.entity.player.task.provider;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.GameMode;

import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class AliveEndTaskProvider implements VaroPlayerStateTaskProvider {

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
