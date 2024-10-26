package de.cuuky.varo.player.event.events;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEvent;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.player.stats.stat.offlinevillager.OfflineVillager;

public class QuitEvent extends BukkitEvent {

	public QuitEvent() {
		super(BukkitEventType.QUIT);
	}

	@Override
	public void onExec(VaroPlayer player) {
		if (Main.getVaroGame().isRunning() && player.getStats().getState() == PlayerState.ALIVE) {
			player.getStats().addInventoryBackup(new InventoryBackup(player));

			if (ConfigSetting.OFFLINEVILLAGER.getValueAsBoolean())
				player.setVillager(new OfflineVillager(player, player.getPlayer().getLocation()));
		}

		if (!player.getStats().hasTimeLeft())
			player.getStats().removeCountdown();

		player.getStats().setLastLocation(player.getPlayer().getLocation());

		player.setAdminIgnore(false);
		player.setPlayer(null);
	}
}