package de.varoplugin.varo.player.event.events;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.BukkitEvent;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.player.stats.stat.PlayerState;
import de.varoplugin.varo.player.stats.stat.inventory.InventoryBackup;
import de.varoplugin.varo.player.stats.stat.offlinevillager.OfflineVillager;

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