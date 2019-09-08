package de.cuuky.varo.gui.admin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.alert.AlertTypeChooseGUI;
import de.cuuky.varo.gui.admin.backup.BackupListGUI;
import de.cuuky.varo.gui.admin.config.ConfigSectionGUI;
import de.cuuky.varo.gui.admin.debug.DebugGUI;
import de.cuuky.varo.gui.admin.discordbot.DiscordBotGUI;
import de.cuuky.varo.gui.admin.game.GameOptionsGUI;
import de.cuuky.varo.gui.admin.orelogger.OreLoggerListGUI;
import de.cuuky.varo.gui.admin.setuphelp.SetupHelpGUI;
import de.cuuky.varo.gui.player.PlayerListChooseGUI;
import de.cuuky.varo.gui.report.ReportListGUI;
import de.cuuky.varo.gui.team.TeamChooseGUI;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.report.Report;
import de.cuuky.varo.team.Team;
import de.cuuky.varo.version.types.Materials;

public class AdminMainMenu extends SuperInventory {

	public AdminMainMenu(Player opener) {
		super(Main.getProjectName() + " §8| §cAdmin", opener, 45, false);

		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(0, new ItemBuilder().displayname("§eSetup Assistant").itemstack(new ItemStack(Materials.ENDER_EYE.parseMaterial())).build(), new Runnable() {

			@Override
			public void run() {
				new SetupHelpGUI(opener);
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("§cConfig").itemstack(new ItemStack(Materials.WHEAT.parseMaterial())).build(), new Runnable() {

			@Override
			public void run() {
				new ConfigSectionGUI(opener);
			}
		});

		linkItemTo(10, new ItemBuilder().displayname("§4Reports").itemstack(new ItemStack(Material.BLAZE_ROD)).amount(getFixedSize(Report.getReports().size())).build(), new Runnable() {

			@Override
			public void run() {
				new ReportListGUI(opener);
			}
		});

		linkItemTo(16, new ItemBuilder().playername(opener.getName()).displayname("§aSpieler").amount(getFixedSize(VaroPlayer.getVaroPlayer().size())).buildSkull(), new Runnable() {

			@Override
			public void run() {
				new PlayerListChooseGUI(opener, true);
			}
		});

		linkItemTo(18, new ItemBuilder().displayname("§cAlerts").itemstack(new ItemStack(Material.BOOK)).amount(getFixedSize(Alert.getOpenAlerts().size())).build(), new Runnable() {

			@Override
			public void run() {
				new AlertTypeChooseGUI(opener);
			}
		});

		linkItemTo(22, new ItemBuilder().displayname("§aBackups").itemstack(Materials.WRITTEN_BOOK.parseItem()).build(), new Runnable() {

			@Override
			public void run() {
				new BackupListGUI(opener);
			}
		});

		linkItemTo(26, new ItemBuilder().displayname("§1DiscordBot").itemstack(new ItemStack(Main.getDiscordBot() != null ? Material.ANVIL : Materials.GUNPOWDER.parseMaterial())).build(), new Runnable() {
			public void run() {
				if(Main.getDiscordBot() == null) {
					opener.sendMessage(Main.getPrefix() + "Der DiscordBot wurde nicht aktiviert.");
					opener.sendMessage(Main.getPrefix() + "Bitte untersuche die Konsolenausgaben nach Fehlern und überprüfe, ob du den DiscordBot aktiviert hast.");
					opener.sendMessage(Main.getPrefix() + "http://www.mediafire.com/file/yzhm845j7ieh678/JDA.jar/file");
					return;
				}

				new DiscordBotGUI(opener);
			}
		});

		linkItemTo(28, new ItemBuilder().displayname("§5Game").itemstack(new ItemStack(Material.CAKE)).build(), new Runnable() {

			@Override
			public void run() {
				new GameOptionsGUI(opener);
			}
		});

		linkItemTo(34, new ItemBuilder().displayname("§2Teams").itemstack(new ItemStack(Material.DIAMOND_HELMET)).amount(getFixedSize(Team.getTeams().size())).build(), new Runnable() {

			@Override
			public void run() {
				new TeamChooseGUI(opener);
			}
		});

		linkItemTo(40, new ItemBuilder().displayname("§6OreLogger").itemstack(new ItemStack(Material.DIAMOND_ORE)).amount(getFixedSize(Main.getLoggerMaster().getBlockLogger().getLogs().size())).build(), new Runnable() {

			@Override
			public void run() {
				new OreLoggerListGUI(opener);
			}
		});

		if(ConfigEntry.DEBUG_OPTIONS.getValueAsBoolean())
			linkItemTo(inv.getSize() - 9, new ItemBuilder().displayname("§6Debug").itemstack(new ItemStack(Material.BUCKET)).build(), new Runnable() {

				@Override
				public void run() {
					new DebugGUI(opener);
				}
			});

		linkItemTo(inv.getSize() - 1, new ItemBuilder().displayname("§5Info").itemstack(new ItemStack(Materials.MAP.parseMaterial())).build(), new Runnable() {

			@Override
			public void run() {
				opener.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lVaro Plugin by Cuuky");
				opener.sendMessage(Main.getPrefix() + "§7Version: " + Main.getColorCode() + Main.getInstance().getDescription().getVersion());
				opener.sendMessage(Main.getPrefix() + "§7Discord: " + Main.getColorCode() + "https://discord.gg/CnDSVVx");
				opener.sendMessage(Main.getPrefix() + "§7All rights reserved!");
			}
		});

		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		return false;
	}
}
