package de.varoplugin.varo.game.entity.player.task.provider;

import de.varoplugin.varo.game.TaskProvider;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.GameMode;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerStateTaskProvider extends TaskProvider<VaroPlayer> {

    GameMode getGameMode();

}