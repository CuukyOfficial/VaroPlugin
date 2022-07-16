package de.varoplugin.varo.task;

import de.varoplugin.varo.api.event.game.player.PlayerParticipantStateChangeEvent;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.VaroParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.List;
import java.util.stream.Collectors;

public class EndGameListener extends VaroListenerTask {

    public EndGameListener(Varo varo) {
        super(varo);
    }

    private boolean isOver(VaroPlayer exclude) {
        List<VaroPlayer> alivePlayers = this.getVaro().getPlayers().filter(vp
                -> !exclude.equals(vp) && vp.isAlive()).collect(Collectors.toList());
        if (alivePlayers.size() <= 1) return true;
        Team team = alivePlayers.get(0).getTeam();
        return alivePlayers.stream().allMatch(vp -> vp.getTeam() == team);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerStateChange(PlayerParticipantStateChangeEvent event) {
        if (event.getState() != VaroParticipantState.DEAD) return;
        if (!this.isOver(event.getPlayer())) return;

        this.getVaro().nextState();
    }
}
