package de.varoplugin.varo.game.task.player;

import de.varoplugin.varo.game.entity.player.ParticipantState;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends AbstractPlayerListener {

    public PlayerDeathListener(VaroPlayer player) {
        super(player);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!this.getPlayer().isPlayer(event.getEntity())) return;
        this.getPlayer().setState(ParticipantState.DEAD);
    }
}
