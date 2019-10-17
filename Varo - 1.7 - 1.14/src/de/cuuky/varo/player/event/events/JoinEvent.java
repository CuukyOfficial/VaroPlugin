package de.cuuky.varo.player.event.events;

import java.util.Date;

import org.bukkit.GameMode;

import de.cuuky.varo.Main;
import de.cuuky.varo.disconnect.Disconnect;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEvent;
import de.cuuky.varo.player.event.BukkitEventType;

public class JoinEvent extends BukkitEvent {

	public JoinEvent() {
		super(BukkitEventType.JOINED);
	}

	@Override
	public void onExec(VaroPlayer player) {
		player.setNormalAttackSpeed();
		if (player.getStats().getFirstTimeJoined() == null)
			player.getStats().setFirstTimeJoined(new Date());

		player.getStats().setLastJoined(new Date());
		player.getStats().setLastLocation(player.getPlayer().getLocation());

		if (player.getVillager() != null) {
			player.getVillager().remove();
			player.setVillager(null);
		}

		if (player.getStats().isWillClear()) {
			player.getStats().clearInventory();
			player.sendMessage(Main.getPrefix() + "Dein Inventar wurde geleert!");
			player.getStats().setWillClear(false);
		}

		if (player.getStats().getRestoreBackup() != null) {
			player.getStats().getRestoreBackup().restoreUpdate(player.getPlayer());
			player.sendMessage(Main.getPrefix() + "Dein Inventar wurde wiederhergestellt!");
			player.getStats().setRestoreBackup(null);
		}

		if (player.getStats().isSpectator() || player.isAdminIgnore())
			player.setSpectacting();
		else
			player.getPlayer().setGameMode(GameMode.SURVIVAL);

		Main.getDataManager().getScoreboardHandler().sendScoreBoard(player);
		Disconnect.joinedAgain(player.getName());
		player.update();
	}
}
