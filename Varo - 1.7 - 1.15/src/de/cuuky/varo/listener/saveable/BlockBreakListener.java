package de.cuuky.varo.listener.saveable;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (!Main.getVaroGame().hasStarted() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
			return;
		}

		Player player = event.getPlayer();
		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
		Block block = event.getBlock();

		if (!(block.getState() instanceof Chest) && !(block.getState() instanceof Furnace))
			return;

		if (!Main.getVaroGame().hasStarted())
			if (varoPlayer.getStats().isSpectator() || !player.hasPermission("varo.setup"))
				return;

		Location loc = block.getLocation();
		VaroSaveable saveable = VaroSaveable.getByLocation(loc);

		if (saveable == null)
			return;
		VaroPlayer holder = saveable.getPlayer();

		if (saveable.canModify(varoPlayer)) {
			player.sendMessage(Main.getPrefix() + ConfigMessages.CHEST_REMOVED_SAVEABLE.getValue(varoPlayer, varoPlayer).replace("%saveable%", block.getState() instanceof Chest ? "Chest" : "Furnace"));
			player.playSound(player.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
			player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			saveable.remove();
			return;
		}

		if (saveable.holderDead())
			return;

		if (!player.hasPermission("varo.ignoreSaveable")) {
			player.sendMessage(Main.getPrefix() + ConfigMessages.CHEST_NOT_TEAM_CHEST.getValue(varoPlayer).replace("%player%", holder.getName()));
			event.setCancelled(true);
		} else {
			player.sendMessage(Main.getPrefix() + "§7Diese Kiste gehoerte " + Main.getColorCode() + saveable.getPlayer().getName() + "§7 aber da du Rechte hast, konntest du sie dennoch zerstoeren!");
			saveable.remove();
		}
	}
}