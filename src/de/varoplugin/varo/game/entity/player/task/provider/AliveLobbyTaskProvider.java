package de.varoplugin.varo.game.entity.player.task.provider;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.task.NoMoveTask;
import org.bukkit.GameMode;

import java.util.Collections;
import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class AliveLobbyTaskProvider implements VaroPlayerStateTaskProvider {

    @Override
    public List<CancelableTask> getTasks(VaroPlayer player) {
        return Collections.singletonList(new NoMoveTask(player));
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.ADVENTURE;
    }
}
