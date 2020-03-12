package de.cuuky.varo.listener.saveable;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.InventoryHolder;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.cuuky.varo.version.types.Sounds;

public class BlockPlaceListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(final BlockPlaceEvent e) {
		if(!Main.getVaroGame().hasStarted())
			return;

		Block placed = e.getBlock();

		if(!(placed.getState() instanceof Chest))
			return;

		Chest chest = (Chest) placed.getState();
		InventoryHolder ih = ((InventoryHolder) chest).getInventory().getHolder();

		if(!(ih instanceof DoubleChest))
			return;

		Chest secChest = (Chest) ((DoubleChest) ih).getLeftSide();

		if(chest.equals(secChest) && secChest != null)
			secChest = (Chest) ((DoubleChest) ih).getRightSide();

		VaroSaveable saveable = VaroSaveable.getByLocation(secChest.getLocation());
		if(saveable == null || saveable.holderDead())
			return;

		Player p = e.getPlayer();
		VaroPlayer player = VaroPlayer.getPlayer(p);

		if(saveable.canModify(player)) {
			new VaroSaveable(SaveableType.CHEST, chest.getLocation(), player);
			player.sendMessage(Main.getPrefix() + ConfigMessages.CHEST_SAVED_SAVEABLE.getValue(player));
			p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
			p.getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 1);
			return;
		}

		e.setCancelled(true);
	}
}