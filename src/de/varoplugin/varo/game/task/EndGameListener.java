package de.varoplugin.varo.game.task;

import de.varoplugin.varo.api.event.game.player.VaroPlayerParticipantStateChangeEvent;
import de.varoplugin.varo.api.task.AbstractListener;
import de.varoplugin.varo.game.DefaultState;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.game.entity.team.VaroTeam;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.util.List;
import java.util.stream.Collectors;

public class EndGameListener extends AbstractListener {

    public EndGameListener(Varo varo) {
        super(varo);
    }

    private boolean isOver(VaroPlayer exclude) {
        List<VaroPlayer> alivePlayers = this.getVaro().getPlayers().filter(vp
                -> !exclude.equals(vp) && vp.isAlive()).collect(Collectors.toList());
        if (alivePlayers.size() <= 1) return true;
        VaroTeam team = alivePlayers.get(0).getTeam();
        return alivePlayers.stream().allMatch(vp -> vp.getTeam() == team);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerStateChange(VaroPlayerParticipantStateChangeEvent event) {
        if (event.getState() != ParticipantState.DEAD) return;
        if (!this.isOver(event.getPlayer())) return;

        this.getVaro().setState(DefaultState.FINISHED);
    }
}
