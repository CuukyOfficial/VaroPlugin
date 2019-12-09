package de.cuuky.varo.entity.player.event.events;

import java.util.Date;

import de.cuuky.varo.game.Game;
import de.cuuky.varo.world.border.VaroBorder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.inventory.InventoryBackup;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.world.border.DecreaseReason;

public class DeadEvent extends BukkitEvent {

	public DeadEvent() {
		super(BukkitEventType.KILLED);
	}

	@Override
	public void onExec(VaroPlayer player) {
		player.getStats().addInventoryBackup(new InventoryBackup(player));

		player.getStats().removeCountdown();
		player.getStats().setDiedAt(new Date());
		player.getStats().setState(PlayerState.DEAD);

		if(ConfigEntry.BACKPACK_DROP_CONTENT_DEATH.getValueAsBoolean())
			if(player.getStats().getBackpack() != null) {
				for(ItemStack item : player.getStats().getBackpack().getInventory().getContents())
					if(item != null && item.getType() != Material.AIR)
						player.getPlayer().getWorld().dropItemNaturally(player.getPlayer().getLocation(), item);

				player.getStats().getBackpack().clear();
			}

		if(Game.getInstance().getGameState() == GameState.STARTED)
			VaroBorder.getInstance().decreaseBorder(DecreaseReason.DEATH);
	}
}
