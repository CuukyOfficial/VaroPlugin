package de.cuuky.varo.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.events.EventListGUI;
import de.cuuky.varo.gui.player.PlayerListChooseGUI;
import de.cuuky.varo.gui.saveable.PlayerSaveableChooseGUI;
import de.cuuky.varo.gui.strike.StrikeListGUI;
import de.cuuky.varo.gui.team.TeamChooseGUI;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.gui.youtube.YouTubeVideoListGUI;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.player.stats.stat.inventory.VaroSaveable;
import de.cuuky.varo.team.Team;
import de.cuuky.varo.utils.LocationFormatter;
import de.cuuky.varo.version.types.Materials;

public class MainMenu extends SuperInventory {

	public MainMenu(Player opener) {
		super(Main.getProjectName(), opener, 36, true);

		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(4, new ItemBuilder().displayname("§5Events").itemstack(new ItemStack(Material.APPLE)).build(), new Runnable() {

			@Override
			public void run() {
				new EventListGUI(opener);
			}
		});

		linkItemTo(10, new ItemBuilder().displayname("§bSpawn").itemstack(new ItemStack(Material.DIAMOND_BLOCK)).lore(new String[] { new LocationFormatter(Main.getColorCode() + "x§7, " + Main.getColorCode() + "y§7, " + Main.getColorCode() + "z").format(opener.getWorld().getSpawnLocation()) }).build(), new Runnable() {

			@Override
			public void run() {
				if(!opener.hasPermission("varo.teleportSpawn"))
					return;

				opener.teleport(opener.getWorld().getSpawnLocation());
			}
		});

		linkItemTo(16, new ItemBuilder().player(opener).amount(getFixedSize(VaroPlayer.getVaroPlayer().size())).displayname("§aSpieler").buildSkull(), new Runnable() {

			@Override
			public void run() {
				new PlayerListChooseGUI(opener, opener.hasPermission("varo.setup"));
			}
		});

		linkItemTo(18, new ItemBuilder().displayname("§6Strikes").itemstack(new ItemStack(Material.PAPER)).amount(getFixedSize(VaroPlayer.getPlayer(opener).getStats().getStrikes().size())).build(), new Runnable() {

			@Override
			public void run() {
				new StrikeListGUI(opener, opener);
			}
		});

		linkItemTo(22, new ItemBuilder().displayname("§eKisten/Öfen").itemstack(new ItemStack(Material.CHEST)).amount(getFixedSize(VaroSaveable.getSaveable(VaroPlayer.getPlayer(opener)).size())).build(), new Runnable() {

			@Override
			public void run() {
				new PlayerSaveableChooseGUI(opener, VaroPlayer.getPlayer(opener));
			}
		});

		linkItemTo(26, new ItemBuilder().displayname("§2Teams").itemstack(new ItemStack(Material.DIAMOND_HELMET)).amount(getFixedSize(Team.getTeams().size())).build(), new Runnable() {

			@Override
			public void run() {
				new TeamChooseGUI(opener);
			}
		});

		linkItemTo(34, new ItemBuilder().displayname("§5Videos").itemstack(new ItemStack(Material.COMPASS)).amount(getFixedSize(YouTubeVideo.getVideos().size())).build(), new Runnable() {

			@Override
			public void run() {
				new YouTubeVideoListGUI(opener);
			}
		});

		if(opener.hasPermission("varo.admin")) {
			linkItemTo(36, new ItemBuilder().displayname("§cAdmin-Section").itemstack(new ItemStack(Materials.OAK_FENCE_GATE.parseMaterial())).build(), new Runnable() {

				@Override
				public void run() {
					new AdminMainMenu(opener);
				}
			});
		}

		if(ConfigEntry.SUPPORT_PLUGIN_ADS.getValueAsBoolean())
			linkItemTo(inv.getSize() - 1, new ItemBuilder().displayname("§5Info").itemstack(new ItemStack(Materials.MAP.parseMaterial())).build(), new Runnable() {

				@Override
				public void run() {
					opener.sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + Main.getPluginName());
					opener.sendMessage(Main.getPrefix() + "§7Version: " + Main.getColorCode() + Main.getInstance().getDescription().getVersion());
					opener.sendMessage(Main.getPrefix() + "§7Discord: " + Main.getColorCode() + "https://discord.gg/CnDSVVx");
					opener.sendMessage(Main.getPrefix() + "§7All rights reserved!");
				}
			});

		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
