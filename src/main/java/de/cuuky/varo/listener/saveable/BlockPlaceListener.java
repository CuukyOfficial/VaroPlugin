package de.cuuky.varo.listener.saveable;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;

import com.cryptomorin.xseries.XSound;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Contexts;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable.SaveableType;

public class BlockPlaceListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (!Main.getVaroGame().hasStarted())
					return;

				Block placed = event.getBlock();

				if (!(placed.getType() == Material.CHEST))
					return;

				Chest chest = (Chest) placed.getState();
				InventoryHolder ih = chest.getInventory().getHolder();

				if (!(ih instanceof DoubleChest))
					return;

				Chest secChest = (Chest) ((DoubleChest) ih).getLeftSide();

				if (chest.equals(secChest) && secChest != null)
					secChest = (Chest) ((DoubleChest) ih).getRightSide();

				VaroSaveable saveable = VaroSaveable.getByLocation(secChest.getLocation());
				if (saveable == null || saveable.holderDead())
					return;

				Player p = event.getPlayer();
				VaroPlayer player = VaroPlayer.getPlayer(p);

				if (saveable.canModify(player)) {
					new VaroSaveable(SaveableType.CHEST, chest.getLocation(), player);
					Messages.PLAYER_CHEST_NEW.send(player, new Contexts.ContainerContext(player, player));
					p.playSound(p.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.get(), 1, 1);
					p.getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 1);
					return;
				}

				event.getBlock().breakNaturally();
			}
		}.runTaskLater(Main.getInstance(), 1L);
	}
}