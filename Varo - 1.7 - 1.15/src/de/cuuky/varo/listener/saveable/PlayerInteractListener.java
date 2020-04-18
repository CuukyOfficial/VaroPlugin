package de.cuuky.varo.listener.saveable;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(!Main.getVaroGame().hasStarted())
			return;

		if(!Main.getVaroGame().hasStarted() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
			return;
		}

		if(event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Player player = event.getPlayer();
		Block block = event.getClickedBlock();

		if(!(block.getState() instanceof Chest) && !(block.getState() instanceof Furnace))
			return;

		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
		VaroSaveable saveable = VaroSaveable.getByLocation(block.getLocation());

		if(saveable == null || saveable.canModify(varoPlayer) || saveable.holderDead())
			return;

		if(!player.hasPermission("varo.ignoreSaveable")) {
			player.sendMessage(Main.getPrefix() + (saveable.getType() == SaveableType.CHEST ? ConfigMessages.CHEST_NOT_TEAM_CHEST.getValue(varoPlayer).replace("%player%", saveable.getPlayer().getName()) : ConfigMessages.CHEST_NOT_TEAM_FURNACE.getValue(varoPlayer).replace("%player%", saveable.getPlayer().getName())));
			event.setCancelled(true);
		} else
			player.sendMessage(Main.getPrefix() + "§7Diese Kiste gehört " + Main.getColorCode() + saveable.getPlayer().getName() + "§7, doch durch deine Rechte konntest du sie trotzdem öffnen!");
	}
}