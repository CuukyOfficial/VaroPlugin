package de.cuuky.varo.api.objects.player.stats;

import java.util.Date;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;

public class VaroAPIInventoryBackup {

	private InventoryBackup backup;

	public VaroAPIInventoryBackup(InventoryBackup backup) {
		this.backup = backup;
	}

	public Date getDateCreated() {
		return backup.getDate();
	}

	public ItemStack[] getInventory() {
		return backup.getAllContents();
	}

	public void restore(Player player) {
		backup.restoreUpdate(player);
	}
}
