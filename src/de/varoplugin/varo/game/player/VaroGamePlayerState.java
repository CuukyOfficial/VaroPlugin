package de.varoplugin.varo.game.player;

import org.bukkit.GameMode;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum VaroGamePlayerState implements VaroPlayerState {

    ALIVE(GameMode.SURVIVAL),
    SPECTATOR(GameMode.ADVENTURE),
    GAME_MASTER(GameMode.CREATIVE),
    DEAD(null);

    private final GameMode gameMode;

    VaroGamePlayerState(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public GameMode getGameMode() {
        return this.gameMode;
    }
}