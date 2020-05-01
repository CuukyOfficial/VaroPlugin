package de.cuuky.varo.gui.team;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.team.VaroTeam;

public class TeamGUI extends SuperInventory {

	private VaroTeam team;

	public TeamGUI(Player opener, VaroTeam team) {
		super("§7Team-ID: §2" + team.getId(), opener, 9, false);

		this.team = team;
		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		return false;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§cSet teamlifes").lore("§7Current§8: §4" + team.getLifes()).itemstack(new ItemStack(Material.DIAMOND)).build(), new Runnable() {

			@Override
			public void run() {
				Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(opener, "§7Enter team colorcode:", new ChatHookHandler() {

					@Override
					public boolean onChat(AsyncPlayerChatEvent event) {
						double lifes = 0;
						try {
							lifes = Double.valueOf(event.getMessage());
						} catch (NumberFormatException e) {
							opener.sendMessage(Main.getPrefix() + "Pleas enter a valid value for team lifes");
							return false;
						}
						
						team.setLifes(lifes);
						opener.sendMessage(Main.getPrefix() + "Team lifes of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + team.getLifes() + "§7'");
						open();
						return true;
					}
				}));
				close(false);
			}
		});

		linkItemTo(3, new ItemBuilder().displayname("§7Set §3name").lore("§7Current§8: " + Main.getColorCode() + team.getDisplay()).itemstack(new ItemStack(Material.DIAMOND_HELMET)).build(), new Runnable() {

			@Override
			public void run() {
				Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(opener, "§7Enter team name:", new ChatHookHandler() {

					@Override
					public boolean onChat(AsyncPlayerChatEvent event) {
						VaroTeam otherTeam = VaroTeam.getTeam(event.getMessage());
						if (otherTeam != null) {
							opener.sendMessage(Main.getPrefix() + "Team name already exists");
							return false;
						}

						team.setName(event.getMessage());
						opener.sendMessage(Main.getPrefix() + "Team name of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + Main.getColorCode() + team.getName() + "§7'");
						open();
						return true;
					}
				}));
				close(false);
			}
		});

		linkItemTo(5, new ItemBuilder().displayname("§7Set §acolorcode").lore("§7Current§8: §5" + team.getColorCode() != null ? (team.getColorCode() + "Like this!") : "-").itemstack(new ItemStack(Material.BOOK)).build(), new Runnable() {

			@Override
			public void run() {
				Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(opener, "§7Enter team colorcode:", new ChatHookHandler() {

					@Override
					public boolean onChat(AsyncPlayerChatEvent event) {
						team.setColorCode(event.getMessage());
						opener.sendMessage(Main.getPrefix() + "Team colorocode of team " + Main.getColorCode() + team.getId() + " §7has been set to '" + team.getDisplay() + "§7'");
						open();
						return true;
					}
				}));
				close(false);
			}
		});
		
		linkItemTo(7, new ItemBuilder().displayname("§4Remove").itemstack(new ItemStack(Material.BUCKET)).build(), new Runnable() {

			@Override
			public void run() {
				team.delete();
				close(true);
			}
		});
		return true;
	}
}