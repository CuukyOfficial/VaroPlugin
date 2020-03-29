package de.cuuky.varo.listener.saveable;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.Sign;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.entity.player.stats.stat.inventory.VaroSaveable.SaveableType;
import de.cuuky.varo.version.types.Materials;
import de.cuuky.varo.version.types.Sounds;

public class SignChangeListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSignChange(SignChangeEvent e) {
		Sign sign = (Sign) e.getBlock().getState().getData();
		if(e.getPlayer().isOp())
			for(int i = 0; i < e.getLines().length; i++)
				e.setLine(i, e.getLines()[i].replace("&", "§"));

		if(e.getBlock().getType() != Materials.SIGN.parseMaterial() && e.getBlock().getType() != Materials.WALL_SIGN.parseMaterial())
			return;

		if(!Main.getVaroGame().hasStarted())
			return;

		Block attached = e.getBlock().getRelative(sign.getAttachedFace());

		if(attached.getState() instanceof Chest) {
			Chest chest = (Chest) attached.getState();
			InventoryHolder ih = ((InventoryHolder) chest).getInventory().getHolder();
			Chest secChest = (ih instanceof DoubleChest ? (Chest) ((DoubleChest) ih).getLeftSide() : null);
			if(chest.equals(secChest) && secChest != null)
				secChest = (Chest) ((DoubleChest) ih).getRightSide();

			if(ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() == 0) {
				e.getPlayer().sendMessage(Main.getPrefix() + "§7Die Chestsicherung wurde in der Config §7deaktiviert!");
				return;
			}

			Player p = e.getPlayer();
			VaroPlayer player = VaroPlayer.getPlayer(p);
			ArrayList<VaroSaveable> teamSaves = VaroSaveable.getSaveable(player);
			ArrayList<VaroSaveable> sorted = new ArrayList<>();

			for(VaroSaveable saves : teamSaves) {
				if(saves.getType() == SaveableType.CHEST)
					sorted.add(saves);
				continue;
			}

			if(sorted.size() >= ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() || secChest != null && sorted.size() + 1 >= ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt()) {
				p.sendMessage(Main.getPrefix() + "§7Die maximale Anzahl an gesetzten Kisten fuer das Team " + Main.getColorCode() + player.getTeam().getName() + " §7wurde bereits §7erreicht! (Anzahl: §6" + sorted.size() + " §7Max: §6" + ConfigSetting.PLAYER_CHEST_LIMIT.getValueAsInt() + "§7)");
				e.setCancelled(true);
				return;
			}

			if(VaroSaveable.getByLocation(chest.getLocation()) != null || secChest != null && VaroSaveable.getByLocation(secChest.getLocation()) != null) {
				p.sendMessage(Main.getPrefix() + "§7Diese " + Main.getColorCode() + " Kiste §7ist bereits gesichert!");
				e.setCancelled(true);
				return;
			}

			if(secChest != null)
				new VaroSaveable(SaveableType.CHEST, secChest.getLocation(), player);

			e.setLine(0, "§8--------------");
			e.setLine(1, "§lSavedChest");
			e.setLine(2, Main.getColorCode() + (player.getTeam() != null ? player.getTeam().getDisplay() : player.getName()));
			e.setLine(3, "§8--------------");
			p.playSound(p.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
			for(int i = 0; i < 6; i++)
				p.getWorld().playEffect(chest.getLocation(), Effect.ENDER_SIGNAL, 1);

			new VaroSaveable(SaveableType.CHEST, chest.getLocation(), player);
			p.sendMessage(Main.getPrefix() + "Kiste erfolgreich gesichert!");
		} else if(attached.getState() instanceof Furnace) {
			Furnace furnace = (Furnace) attached.getState();

			if(ConfigSetting.PLAYER_FURNACE_LIMIT.getValueAsInt() == 0) {
				e.getPlayer().sendMessage(Main.getPrefix() + "§7Die Furnacesicherung wurde in der Config §7deaktiviert!");
				return;
			}

			Player p = e.getPlayer();
			VaroPlayer player = VaroPlayer.getPlayer(p);

			ArrayList<VaroSaveable> teamSaves = VaroSaveable.getSaveable(player);
			ArrayList<VaroSaveable> sorted = new ArrayList<>();

			for(VaroSaveable saves : teamSaves) {
				if(saves.getType() == SaveableType.FURNANCE)
					sorted.add(saves);
				continue;
			}

			if(VaroSaveable.getByLocation(furnace.getLocation()) != null) {
				p.sendMessage(Main.getPrefix() + "§7Diese " + Main.getColorCode() + " Furnace §7ist bereits gesichert!");
				e.setCancelled(true);
				return;
			}

			if(ConfigSetting.PLAYER_FURNACE_LIMIT.isIntActivated())
				if(sorted.size() >= ConfigSetting.PLAYER_FURNACE_LIMIT.getValueAsInt()) {
					p.sendMessage(Main.getPrefix() + "§7Die maximale Anzahl an gesetzten Furnaces fuer das Team " + Main.getProjectName() + " " + player.getTeam().getDisplay() + " §7wurde bereits §7erreicht! (Anzahl: §6" + sorted.size() + " §7Max: §6" + ConfigSetting.PLAYER_FURNACE_LIMIT.getValueAsInt() + "§7)");
					e.setCancelled(true);
					return;
				}

			e.setLine(0, "§8--------------");
			e.setLine(1, "§lSavedFurnace");
			e.setLine(2, Main.getColorCode() + (player.getTeam() != null ? player.getTeam().getDisplay() : player.getName()));
			e.setLine(3, "§8--------------");
			p.playSound(furnace.getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
			p.getWorld().playEffect(furnace.getLocation(), Effect.ENDER_SIGNAL, 1);
			p.getWorld().playEffect(furnace.getLocation(), Effect.ENDER_SIGNAL, 1);
			p.getWorld().playEffect(furnace.getLocation(), Effect.ENDER_SIGNAL, 1);
			p.getWorld().playEffect(furnace.getLocation(), Effect.ENDER_SIGNAL, 1);
			p.getWorld().playEffect(furnace.getLocation(), Effect.ENDER_SIGNAL, 1);
			p.getWorld().playEffect(furnace.getLocation(), Effect.ENDER_SIGNAL, 1);
			new VaroSaveable(SaveableType.FURNANCE, furnace.getBlock().getLocation(), player);
			p.sendMessage(Main.getPrefix() + "Ofen erfolgreich gesichert!");
		}
	}
}