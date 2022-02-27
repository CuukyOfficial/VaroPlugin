package de.cuuky.varo.heartbeat;

import de.cuuky.varo.Varo;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.heartbeat.listener.NoBuildListener;
import de.cuuky.varo.heartbeat.listener.NoMoveListener;
import org.bukkit.GameMode;

public class LobbyHeartbeat extends UpdateHeartbeat {

    public LobbyHeartbeat(Varo varo) {
        super(varo);

        this.addPlayerListener(new PlayerListenerBuilder(NoMoveListener::new).requirements(this::isSurvival));
        this.addPlayerListener(new PlayerListenerBuilder(NoBuildListener::new).requirements(this::isSurvival));
    }

    private boolean isSurvival(VaroPlayer player) {
        return player.getPlayer().getGameMode() == GameMode.SURVIVAL;
    }
}