package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.register.VaroPlayerTaskInfo;

/**
 * Triggers all player specific tasks.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerTrigger<I extends VaroPlayerTaskInfo> extends VaroPlayerTask<I>, VaroTaskTrigger<I> {

}