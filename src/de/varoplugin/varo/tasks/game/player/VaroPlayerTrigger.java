package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.tasks.VaroTaskTrigger;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerTrigger extends VaroTaskTrigger {

    boolean register(VaroPlayer player);

}