package de.cuuky.varo.entity.player.event.events;

import java.util.Date;

import org.bukkit.GameMode;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

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

		if (player.getStats().isSpectator() || player.isAdminIgnore()) {
			player.setSpectacting();
			player.sendMessage(Main.getPrefix() + "Da Du §c" + (player.isAdminIgnore() ? "als Admin gejoint bist und keine Folgen mehr produzieren darfst" : "Spectator bist") + " §7wurdest du in den Zuschauer-Modus gesetzt!");
		} else
			player.getPlayer().setGameMode(GameMode.SURVIVAL);

		VaroPlayerDisconnect.joinedAgain(player.getName());
	}
}