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
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.cuuky.varo.game.VaroGame;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(!VaroGame.getInstance().hasStarted())
			return;

		if(!VaroGame.getInstance().hasStarted() && e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
			return;
		}

		if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		Player player = e.getPlayer();
		Block block = e.getClickedBlock();

		if(!(block.getState() instanceof Chest) && !(block.getState() instanceof Furnace))
			return;

		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
		VaroSaveable saveable = VaroSaveable.getByLocation(block.getLocation());

		if(saveable == null || saveable.canModify(varoPlayer) || saveable.holderDead())
			return;

		if(!player.hasPermission("varo.ignoreSaveable")) {
			player.sendMessage(Main.getPrefix() + (saveable.getType() == SaveableType.CHEST ? ConfigMessages.NOT_TEAM_CHEST.getValue().replace("%player%", saveable.getPlayer().getName()) : ConfigMessages.NOT_TEAM_FURNACE.getValue().replace("%player%", saveable.getPlayer().getName())));
			e.setCancelled(true);
		} else
			player.sendMessage(Main.getPrefix() + "§7Diese Kiste gehört " + Main.getColorCode() + saveable.getPlayer().getName() + "§7, doch durch deine Rechte konntest du sie trotzdem öffnen!");
	}
}