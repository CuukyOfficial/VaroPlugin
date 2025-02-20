package de.cuuky.varo.listener.saveable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Contexts;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!Main.getVaroGame().hasStarted())
			return;

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

		if (block.getType() != Material.CHEST && block.getType() != Material.FURNACE)
			return;

		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
		VaroSaveable saveable = VaroSaveable.getByLocation(block.getLocation());

		if (saveable == null || saveable.canModify(varoPlayer) || saveable.holderDead())
			return;

		if (!player.hasPermission("varo.ignoreSaveable")) {
		    Messages.PLAYER_CHEST_DISALLOWED.send(varoPlayer, new Contexts.ContainerContext(varoPlayer, saveable.getPlayer()));
			e.setCancelled(true);
		} else
		    Messages.PLAYER_CHEST_ADMIN.send(varoPlayer, new Contexts.ContainerContext(varoPlayer, saveable.getPlayer()));
	}
}