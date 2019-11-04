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

import de.cuuky.varo.Main;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.version.types.Sounds;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!Main.getGame().hasStarted() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
			return;
		}

		Player player = event.getPlayer();
		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
		Block block = event.getBlock();

		if(!(block.getState() instanceof Chest) && !(block.getState() instanceof Furnace))
			return;

		if(!Main.getGame().hasStarted())
			if(varoPlayer.getStats().isSpectator() || !player.hasPermission("varo.setup"))
				return;

		Location loc = block.getLocation();
		VaroSaveable saveable = VaroSaveable.getByLocation(loc);

		if(saveable == null)
			return;
		VaroPlayer holder = saveable.getPlayer();

		if(saveable.canModify(varoPlayer)) {
			player.sendMessage(Main.getPrefix() + ConfigMessages.REMOVED_SAVEABLE.getValue().replaceAll("%saveable%", block.getState() instanceof Chest ? "Chest" : "Furnace"));
			player.playSound(player.getLocation(), Sounds.NOTE_BASS_DRUM.bukkitSound(), 1, 1);
			player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			saveable.remove();
			return;
		}

		if(saveable.holderDead())
			return;

		if(!player.hasPermission("varo.ignoreSaveable")) {
			player.sendMessage(Main.getPrefix() + ConfigMessages.NOT_TEAM_CHEST.getValue().replaceAll("%player%", holder.getName()));
			event.setCancelled(true);
		} else {
			player.sendMessage(Main.getPrefix() + "§7Diese Kiste gehörte " + Main.getColorCode() + saveable.getPlayer().getName() + "§7 aber da du Rechte hast, konntest du sie dennoch zerstören!");
			saveable.remove();
		}
	}
}
