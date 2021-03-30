package de.cuuky.varo.gui.player;

import java.net.URL;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.hooking.hooks.chat.ChatHook;
import de.cuuky.cfw.hooking.hooks.chat.ChatHookHandler;
import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.gui.VaroSuperInventory;
import de.cuuky.varo.gui.player.PlayerListGUI.PlayerGUIType;

public class PlayerOptionsGUI extends VaroSuperInventory {

	private Stats stats;
	private VaroPlayer target;
	private PlayerGUIType type;

	public PlayerOptionsGUI(Player opener, VaroPlayer target, PlayerGUIType type) {
		super("§2" + target.getName() + " §7(" + target.getId() + ")", opener, 54, false);

		this.target = target;
		this.stats = target.getStats();
		this.type = type;

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	private boolean isURl(String link) {
		try {
			new URL(link).openConnection();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void updateStats() {
		if (getPage() == 1) {
			inv.setItem(4, new ItemBuilder().displayname("§cKills").itemstack(new ItemStack(Material.DIAMOND_SWORD)).lore(new String[] { "§7Current: " + stats.getKills() }).build());
			inv.setItem(13, new ItemBuilder().displayname("§6Rank").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[] { "§7Current: " + (target.getRank() == null ? "/" : target.getRank().getDisplay()) }).build());
			inv.setItem(22, new ItemBuilder().displayname("§5YouTube-Link").itemstack(new ItemStack(Materials.PAPER.parseMaterial())).lore(new String[] { "§7Current: " + stats.getYoutubeLink() }).build());
			inv.setItem(31, new ItemBuilder().displayname("§bSessions").itemstack(new ItemStack(Material.DIAMOND)).lore(new String[] { "§7Current: " + stats.getSessions() }).build());
		} else if (getPage() == 2) {
			inv.setItem(4, new ItemBuilder().displayname("§5EpisodesPlayed").itemstack(new ItemStack(Material.BLAZE_POWDER)).lore(new String[] { "§7Current: " + stats.getSessionsPlayed() }).build());
			inv.setItem(13, new ItemBuilder().displayname("§6Countdown").itemstack(new ItemStack(Materials.SIGN.parseMaterial())).lore(new String[] { "§7Current: " + stats.getCountdown() }).build());

			inv.setItem(36, new ItemBuilder().displayname("§7Change §cWill InventoryClear").itemstack(new ItemStack(Material.ARROW)).lore(new String[] { "§7Current: " + stats.isWillClear() }).build());
			inv.setItem(37, new ItemBuilder().displayname("§7Change §6State").itemstack(new ItemStack(Material.GOLDEN_APPLE)).lore(new String[] { "§7Current: " + stats.getState().getName() }).build());
			inv.setItem(38, new ItemBuilder().displayname("§7Remove §cTimeUntilAddSession").itemstack(new ItemStack(Materials.PAPER.parseMaterial())).lore(new String[] { "§7Current: " + (stats.getTimeUntilAddSession()) }).build());
		}
	}

	public VaroPlayer getTarget() {
		return target;
	}

	@Override
	public boolean onBackClick() {
		new PlayerGUI(opener, target, type);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		String itemname = event.getCurrentItem().getItemMeta().getDisplayName();

		if (itemname.contains("EpisodesPlayed")) {
			if (itemname.contains("Add"))
				stats.addSessionPlayed();
			else if (itemname.contains("Remove"))
				stats.setSessionsPlayed(stats.getSessionsPlayed() - 1);
		}

		if (itemname.contains("Countdown")) {
			if (itemname.contains("Reset"))
				stats.removeCountdown();
			else if (itemname.contains("Remove"))
				stats.setCountdown(1);
		}

		if (itemname.contains("Will InventoryClear"))
			stats.setWillClear(!stats.isWillClear());

		if (itemname.contains("Kill")) {
			if (itemname.contains("Add"))
				stats.addKill();
			else if (itemname.contains("Remove"))
				stats.setKills(stats.getKills() - 1);
		}

		if (itemname.contains("Session")) {
			if (itemname.contains("Add"))
				stats.setSessions(stats.getSessions() + 1);
			else if (itemname.contains("Remove"))
				stats.setSessions(stats.getSessions() - 1);
		}

		if (itemname.contains("Link")) {
			if (itemname.contains("Set")) {
				close(false);

				Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(opener, "§7Enter Youtube-Link:", new ChatHookHandler() {

					@Override
					public boolean onChat(AsyncPlayerChatEvent event) {
						if (!isURl(event.getMessage())) {
							opener.sendMessage(Main.getPrefix() + "Das ist kein Link!");
							return false;
						}

						stats.setYoutubeLink(event.getMessage());
						opener.sendMessage(Main.getPrefix() + "Youtubelink gesetzt!");
						reopenSoon();
						return true;
					}
				}));
			} else if (itemname.contains("Remove"))
				stats.setYoutubeLink(null);
		}

		if (itemname.contains("Rank")) {
			if (itemname.contains("Set")) {
				close(false);

				Main.getCuukyFrameWork().getHookManager().registerHook(new ChatHook(opener, "§7Enter Rank", new ChatHookHandler() {

					@Override
					public boolean onChat(AsyncPlayerChatEvent event) {
						target.setRank(new Rank(event.getMessage()));
						opener.sendMessage(Main.getPrefix() + "Rang gesetzt!");
						reopenSoon();
						return true;
					}
				}));
			} else if (itemname.contains("Remove"))
				target.setRank(null);
		}

		if (itemname.contains("State")) {
			PlayerState state = null;
			switch (target.getStats().getState()) {
			case DEAD:
				state = PlayerState.SPECTATOR;
				break;
			case ALIVE:
				state = PlayerState.DEAD;
				break;
			case SPECTATOR:
				state = PlayerState.ALIVE;
				break;
			}

			stats.setState(state);
		}

		if (itemname.contains("TimeUntilAddSession")) {
			stats.setTimeUntilAddSession(null);
			stats.setSessions(stats.getSessions() + 1);
		}

		updateStats();
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		if (getPage() == 1) {
			inv.setItem(8, new ItemBuilder().displayname("§aAdd Kill").itemstack(new ItemStack(Material.APPLE)).build());
			inv.setItem(0, new ItemBuilder().displayname("§cRemove Kill").itemstack(Materials.REDSTONE.parseItem()).build());

			inv.setItem(17, new ItemBuilder().displayname("§aSet Rank").itemstack(new ItemStack(Material.APPLE)).build());
			inv.setItem(9, new ItemBuilder().displayname("§cRemove Rank").itemstack(Materials.REDSTONE.parseItem()).build());

			inv.setItem(26, new ItemBuilder().displayname("§aSet Link").itemstack(new ItemStack(Material.APPLE)).build());
			inv.setItem(18, new ItemBuilder().displayname("§cRemove Link").itemstack(Materials.REDSTONE.parseItem()).build());

			inv.setItem(35, new ItemBuilder().displayname("§aAdd Session").itemstack(new ItemStack(Material.APPLE)).build());
			inv.setItem(27, new ItemBuilder().displayname("§cRemove Session").itemstack(Materials.REDSTONE.parseItem()).build());
		} else if (getPage() == 2) {
			inv.setItem(8, new ItemBuilder().displayname("§aAdd EpisodesPlayed").itemstack(new ItemStack(Material.APPLE)).build());
			inv.setItem(0, new ItemBuilder().displayname("§cRemove EpisodesPlayed").itemstack(Materials.REDSTONE.parseItem()).build());

			inv.setItem(17, new ItemBuilder().displayname("§aReset Countdown").itemstack(new ItemStack(Material.APPLE)).build());
			inv.setItem(9, new ItemBuilder().displayname("§cRemove Countdown").itemstack(Materials.REDSTONE.parseItem()).build());
		}

		updateStats();
		return getPage() == 2;
	}
}
