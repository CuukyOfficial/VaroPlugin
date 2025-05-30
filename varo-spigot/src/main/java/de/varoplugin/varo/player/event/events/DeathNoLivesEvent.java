package de.varoplugin.varo.player.event.events;

import java.util.Date;

import org.bukkit.Location;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.world.border.decrease.DecreaseReason;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.player.stats.stat.PlayerState;

public class DeathNoLivesEvent extends AbstractDeathEvent {

	public DeathNoLivesEvent() {
		super(BukkitEventType.DEATH_NO_LIVES);
	}

	@Override
	public void onExec(VaroPlayer player) {
        Location location = player.isOnline() ? player.getPlayer().getLocation() : player.getStats().getLastLocation();
		player.getStats().removeCountdown();
		player.getStats().setDiedAt(new Date());
		player.getStats().setState(PlayerState.DEAD);

		if (Main.getVaroGame().isRunning())
			Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.DEATH);

        if (ConfigSetting.BACKPACK_PLAYER_DROP_ON_DEATH.getValueAsBoolean())
            if (player.getStats().getPlayerBackpack() != null)
                this.dropInventory(player.getStats().getPlayerBackpack(), location);

        if (ConfigSetting.BACKPACK_TEAM_DROP_ON_DEATH.getValueAsBoolean())
            if (player.getTeam() != null && player.getTeam().isDead() && player.getTeam().getTeamBackPack() != null)
                this.dropInventory(player.getTeam().getTeamBackPack(), location);
	}
}