package de.cuuky.varo.entity.player.event.events;

import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.Stats;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.Date;

public class JoinEvent extends BukkitEvent {

	public JoinEvent() {
		super(BukkitEventType.JOINED);
	}

	@Override
	public void onExec(VaroPlayer player) {
		Stats stats = player.getStats();
		player.setNormalAttackSpeed();
		if (stats.getFirstTimeJoined() == null)
			stats.setFirstTimeJoined(new Date());

		stats.setLastJoined(new Date());
		stats.setLastLocation(player.getPlayer().getLocation());
		
		if(!stats.isOnlineAfterStart()) {
			stats.setOnlineAfterStart();
			player.cleanUpPlayer();
			player.saveTeleport(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation());
		}

		if (player.getVillager() != null) {
			player.getVillager().remove();
			player.setVillager(null);
		}

		if (stats.isWillClear()) {
			stats.clearInventory();
			player.sendMessage(Main.getPrefix() + "Dein Inventar wurde geleert!");
			stats.setWillClear(false);
		}

		if (stats.getRestoreBackup() != null) {
			stats.getRestoreBackup().restore(player.getPlayer());
			player.sendMessage(Main.getPrefix() + "Dein Inventar wurde wiederhergestellt!");
			stats.setRestoreBackup(null);
		}

		if (stats.isSpectator() || player.isAdminIgnore()) {
			player.setSpectacting();
			player.sendMessage(Main.getPrefix() + "Da Du §c" + (player.isAdminIgnore() ? "als Admin gejoint bist und keine Folgen mehr produzieren darfst" : "Spectator bist") + " §7wurdest du in den Zuschauer-Modus gesetzt!");
		} else {
			player.getPlayer().setGameMode(GameMode.SURVIVAL);
			for (Player pl1 : VersionUtils.getVersionAdapter().getOnlinePlayers())
				pl1.showPlayer(player.getPlayer());
		}

		VaroPlayerDisconnect.joinedAgain(player.getPlayer());
	}
}