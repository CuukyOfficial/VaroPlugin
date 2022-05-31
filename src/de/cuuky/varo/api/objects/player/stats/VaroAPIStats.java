package de.cuuky.varo.api.objects.player.stats;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import de.cuuky.varo.api.objects.player.VaroAPIState;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;

public class VaroAPIStats {

	private Stats stats;

	public VaroAPIStats(Stats stats) {
		this.stats = stats;
	}

	public void addKill() {
		stats.addKill();
	}

	public int getCountdown() {
		return stats.getCountdown();
	}

	public boolean isCountdownPaused() {
		return stats.isCountdownPaused();
	}

	public ArrayList<VaroAPIInventoryBackup> getInventoryBackups() {
		ArrayList<VaroAPIInventoryBackup> backups = new ArrayList<>();
		for (InventoryBackup invB : stats.getInventoryBackups())
			backups.add(new VaroAPIInventoryBackup(invB));

		return backups;
	}

	public int getKills() {
		return stats.getKills();
	}

	public Location getLastLocation() {
		return stats.getLastLocation();
	}

	public VaroAPIState getState() {
		return VaroAPIState.getState(stats.getState());
	}

	public List<VaroAPIStrike> getStrikes() {
		List<VaroAPIStrike> strikes = new ArrayList<VaroAPIStrike>();
		for (Strike strike : stats.getStrikes())
			strikes.add(new VaroAPIStrike(strike));

		return strikes;
	}

	public void setCountdown(int time) {
		stats.setCountdown(time);
	}

	public void setState(VaroAPIState state) {
		stats.setState(state.getOrigin());
	}

	public void setWillClearInventory(boolean willClear) {
		stats.setWillClear(willClear);
	}

	public void setCountdownPaused(boolean paused) {
		stats.setCountdownPaused(paused);
	}

}
