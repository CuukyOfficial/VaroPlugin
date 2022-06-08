package de.varoplugin.varo.game.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.tasks.TaskTrigger;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerTrigger extends TaskTrigger {

    boolean register(VaroPlayer player);

}