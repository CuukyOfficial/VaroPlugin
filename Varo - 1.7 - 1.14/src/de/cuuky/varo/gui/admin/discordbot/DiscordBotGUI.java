package de.cuuky.varo.gui.admin.discordbot;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;

public class DiscordBotGUI extends SuperInventory {

	public DiscordBotGUI(Player opener) {
		super("§2DiscordBot", opener, 9, false);

		open();
	}

	@Override
	public boolean onOpen() {

		linkItemTo(1, new ItemBuilder().displayname(BotLauncher.getDiscordBot().isEnabled() ? "§cShutdown" : "§aStart").itemstack(new ItemStack(BotLauncher.getDiscordBot().isEnabled() ? Material.REDSTONE : Material.EMERALD)).build(), new Runnable() {

			@Override
			public void run() {
				boolean enabled = BotLauncher.getDiscordBot().isEnabled();
				if(enabled)
					BotLauncher.getDiscordBot().disconnect();
				else
					BotLauncher.getDiscordBot().connect();

				if(BotLauncher.getDiscordBot().isEnabled() == enabled)
					opener.sendMessage(Main.getPrefix() + "§7Could not start DiscordBot.");
				else
					opener.sendMessage(Main.getPrefix() + "§7Erfolg!");
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("§eBotRegister").itemstack(new ItemStack(Material.BOOK)).build(), new Runnable() {

			@Override
			public void run() {
				if(BotLauncher.getDiscordBot().isEnabled() || !ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
					opener.sendMessage(Main.getPrefix() + "Das System ist nicht aktiviert!");
					return;
				}
			}
		});

		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		updateInventory();
	}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}
}
