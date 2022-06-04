package de.varoplugin.varo.game.player.state;

import de.varoplugin.varo.game.CancelableListener;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.player.VaroPlayer;

import java.util.List;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerState {

    List<CancelableListener> getListener(VaroState state, VaroPlayer player);

    boolean addInfo(PlayerInfo playerInfo);

}