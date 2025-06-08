package de.varoplugin.varo.listener.saveable;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.cryptomorin.xseries.XSound;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Contexts;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.inventory.VaroSaveable;

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

		if (block.getType() != Material.CHEST && block.getType() != Material.FURNACE)
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
			Messages.PLAYER_CHEST_REMOVED.send(varoPlayer, new Contexts.ContainerContext(varoPlayer, holder));
			player.playSound(player.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASEDRUM.get(), 1, 1);
			for (int i = 0; i < 3; i++)
				player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 1);
			saveable.remove();
			return;
		}

		if (saveable.holderDead())
			return;

		if (!player.hasPermission("varo.ignoreSaveable")) {
		    Messages.PLAYER_CHEST_DISALLOWED.send(varoPlayer, new Contexts.ContainerContext(varoPlayer, holder));
			event.setCancelled(true);
		} else {
			Messages.PLAYER_CHEST_ADMIN.send(varoPlayer, new Contexts.ContainerContext(varoPlayer, holder));
			saveable.remove();
		}
	}
}