package de.varoplugin.varo.game.player.info;

import de.varoplugin.varo.game.CancelableTask;
import de.varoplugin.varo.game.VaroGameState;
import de.varoplugin.varo.game.player.VaroPlayer;
import de.varoplugin.varo.game.player.tasks.NoMoveTask;
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
    public List<CancelableTask> getTasks(VaroPlayer player) {
        return Arrays.asList(new NoMoveTask(player));
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.ADVENTURE;
    }
}
