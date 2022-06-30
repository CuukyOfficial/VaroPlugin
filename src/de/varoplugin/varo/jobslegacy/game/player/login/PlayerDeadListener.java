package de.varoplugin.varo.jobslegacy.game.player.login;

import de.varoplugin.varo.game.entity.player.GamePlayerKickResult;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.player.VaroPlayerKickResult;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerDeadListener extends RunningPlayerLoginListener {

    public PlayerDeadListener(VaroPlayer player) {
        super(player);
    }

    @Override
    protected VaroPlayerKickResult onVaroPlayerLogin(PlayerLoginEvent event) {
        return this.getPlayer().getState() == ParticipantState.DEAD ?
                GamePlayerKickResult.PLAYER_DEAD : GamePlayerKickResult.ALLOWED;
    }
}
