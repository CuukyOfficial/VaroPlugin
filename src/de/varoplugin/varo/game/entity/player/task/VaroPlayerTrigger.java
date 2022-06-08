package de.varoplugin.varo.game.entity.player.task;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.tasks.VaroTaskTrigger;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerTrigger extends VaroTaskTrigger {

    boolean register(VaroPlayer player);

}