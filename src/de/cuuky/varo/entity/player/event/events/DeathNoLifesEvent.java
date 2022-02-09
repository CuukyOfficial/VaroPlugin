package de.cuuky.varo.entity.player.event.events;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.world.border.decrease.DecreaseReason;
import org.bukkit.Location;

import java.util.Date;

public class DeathNoLifesEvent extends AbstractDeathEvent {

	public DeathNoLifesEvent() {
		super(BukkitEventType.DEATH_NO_LIFES);
	}

	@Override
	public void onExec(VaroPlayer player) {
        Location location = player.isOnline() ? player.getPlayer().getLocation() : player.getStats().getLastLocation();
		player.getStats().removeCountdown();
		player.getStats().setDiedAt(new Date());
		player.getStats().setState(PlayerState.DEAD);

		if (Main.getVaroGame().getGameState() == GameState.STARTED)
			Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.DEATH);

        if (ConfigSetting.BACKPACK_PLAYER_DROP_ON_DEATH.getValueAsBoolean())
            if (player.getStats().getPlayerBackpack() != null)
                this.dropInventory(player.getStats().getPlayerBackpack(), location);

        if (ConfigSetting.BACKPACK_TEAM_DROP_ON_DEATH.getValueAsBoolean())
            if (player.getTeam() != null && player.getTeam().isDead() && player.getTeam().getTeamBackPack() != null)
                this.dropInventory(player.getTeam().getTeamBackPack(), location);
	}
}