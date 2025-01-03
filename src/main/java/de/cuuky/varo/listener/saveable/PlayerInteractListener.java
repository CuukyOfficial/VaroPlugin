package de.cuuky.varo.listener.saveable;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable.SaveableType;

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
			player.sendMessage((saveable.getType() == SaveableType.CHEST ? ConfigMessages.CHEST_NOT_TEAM_CHEST.getValue(varoPlayer).replace("%player%", saveable.getPlayer().getName()) : ConfigMessages.CHEST_NOT_TEAM_FURNACE.getValue(varoPlayer).replace("%player%", saveable.getPlayer().getName())));
			e.setCancelled(true);
		} else
			player.sendMessage("§7Diese(r) " + saveable.getType().toString() + " gehoert " + Main.getColorCode() + saveable.getPlayer().getName() + "§7, doch durch deine Rechte konntest du sie trotzdem oeffnen!");
	}
}