package de.varoplugin.varo.game.entity.player;

import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.game.entity.player.info.PlayerInfo;

/**
 * Represents a player state a @{@link VaroPlayer} can be.
 * It provides @{@link PlayerInfo} to let the player know how to behave.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public interface VaroPlayerState {

    PlayerInfo getInfo(VaroState state);

    boolean addInfo(PlayerInfo playerInfo);

}