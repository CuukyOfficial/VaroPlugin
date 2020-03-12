package de.cuuky.varo.game.lobby;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.lobby.lobbyitems.LeaveTeamItem;
import de.cuuky.varo.game.lobby.lobbyitems.TeamRequestItem;

public class LobbyItem {

	private static ArrayList<LobbyItem> lobbyItems;

	static {
		Bukkit.getPluginManager().registerEvents(new LobbyItemsListener(), Main.getInstance());
		lobbyItems = new ArrayList<>();

		if(ConfigSetting.TEAMREQUESTS.getValueAsBoolean()) {
			new TeamRequestItem();
			new LeaveTeamItem();
		}
	}

	private ItemStack item;
	private int slot;

	public LobbyItem(ItemStack stack, int slot) {
		this.item = stack;
		this.slot = slot;

		lobbyItems.add(this);
	}

	public ItemStack getItem() {
		return item;
	}

	public int getSlot() {
		return slot;
	}

	public void onEntityHit(EntityDamageByEntityEvent event) {}

	public void onInteract(PlayerInteractEvent event) {}

	public void onInteractEntity(PlayerInteractEntityEvent event) {}

	public static LobbyItem getLobbyItem(ItemStack item) {
		for(LobbyItem lItem : lobbyItems)
			if(lItem.getItem().equals(item))
				return lItem;

		return null;
	}

	public static void giveItems(Player player) {
		for(LobbyItem item : lobbyItems)
			player.getInventory().setItem(item.getSlot(), item.getItem());

		player.updateInventory();
	}
}
