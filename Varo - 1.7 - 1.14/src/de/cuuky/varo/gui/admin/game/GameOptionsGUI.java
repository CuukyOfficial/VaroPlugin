package de.cuuky.varo.gui.admin.game;

import de.cuuky.varo.game.Game;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.utils.LocationFormatter;

public class GameOptionsGUI extends SuperInventory {

	public GameOptionsGUI(Player opener) {
		super("Game", opener, 9, false);

		open();
	}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§aChange GameState").itemstack(new ItemStack(Material.EMERALD)).lore(new String[] { "§7Current: §c" + Game.getInstance().getGameState().getName() }).build(), new Runnable() {

			@Override
			public void run() {
				switch(Game.getInstance().getGameState()) {
				case STARTED:
					Game.getInstance().setGamestate(GameState.END);
					break;
				case END:
					Game.getInstance().setGamestate(GameState.LOBBY);
					break;
				case LOBBY:
					Game.getInstance().setGamestate(GameState.STARTED);
					break;
				}
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("§bSet Lobby Location").itemstack(new ItemStack(Material.DIAMOND_BLOCK)).lore(new String[] { "§7Current: " + (Game.getInstance().getLobby() != null ? new LocationFormatter("x, y, z in world").format(Game.getInstance().getLobby()) : "§c-") }).build(), new Runnable() {

			@Override
			public void run() {
				Game.getInstance().setLobby(opener.getLocation());
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("§2Set World Spawn").itemstack(new ItemStack(Material.BEACON)).lore(new String[] { "§7Current: " + (opener.getWorld().getSpawnLocation() != null ? new LocationFormatter("x, y, z in world").format(opener.getWorld().getSpawnLocation()) : "§c-") }).build(), new Runnable() {

			@Override
			public void run() {
				opener.getWorld().setSpawnLocation(opener.getLocation().getBlockX(), opener.getLocation().getBlockY(), opener.getLocation().getBlockZ());
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
