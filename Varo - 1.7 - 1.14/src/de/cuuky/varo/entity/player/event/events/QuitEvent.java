package de.cuuky.varo.entity.player.event.events;

import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.entity.player.stats.stat.offlinevillager.OfflineVillager;
import de.cuuky.varo.game.Game;

public class QuitEvent extends BukkitEvent {

	public QuitEvent() {
		super(BukkitEventType.QUIT);
	}

	@Override
	public void onExec(VaroPlayer player) {
		if(Game.getInstance().isRunning() && player.getStats().getState() == PlayerState.ALIVE) {
			player.getStats().addInventoryBackup(new InventoryBackup(player));

			if(ConfigEntry.OFFLINEVILLAGER.getValueAsBoolean())
				player.setVillager(new OfflineVillager(player, player.getPlayer().getLocation()));
		}

		if(!player.getStats().hasTimeLeft())
			player.getStats().removeCountdown();

		player.getStats().setLastLocation(player.getPlayer().getLocation());
		if(player.getNametag() != null)
			player.getNametag().remove();
		player.setNametag(null);
		player.setNetworkManager(null);
		player.setAdminIgnore(false);
		player.setPlayer(null);
	}
}