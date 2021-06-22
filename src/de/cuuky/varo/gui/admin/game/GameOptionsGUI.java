package de.cuuky.varo.gui.admin.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.varo.Main;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.gui.VaroSuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;

public class GameOptionsGUI extends VaroSuperInventory {

	public GameOptionsGUI(Player opener) {
		super("Game", opener, 18, false);

		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		updateInventory();
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		linkItemTo(1, new ItemBuilder().displayname("§aChange GameState").itemstack(new ItemStack(Material.EMERALD)).lore(new String[] { "§7Current: §c" + Main.getVaroGame().getGameState().getName() }).build(), new Runnable() {

			@Override
			public void run() {
				switch (Main.getVaroGame().getGameState()) {
				case STARTED:
					Main.getVaroGame().setGamestate(GameState.END);
					break;
				case END:
					Main.getVaroGame().setGamestate(GameState.LOBBY);
					break;
				case LOBBY:
					Main.getVaroGame().setGamestate(GameState.STARTED);
					break;
				}
			}
		});

		linkItemTo(7, new ItemBuilder().displayname("§bSet Lobby Location").itemstack(new ItemStack(Material.DIAMOND_BLOCK)).lore(new String[] { "§7Current: " + (Main.getVaroGame().getLobby() != null ? new LocationFormat(Main.getVaroGame().getLobby()).format("x, y, z in world") : "§c-") }).build(), new Runnable() {

			@Override
			public void run() {
				Main.getVaroGame().setLobby(opener.getLocation());
			}
		});

		linkItemTo(4, new ItemBuilder().displayname("§2Set World Spawn").itemstack(new ItemStack(Material.BEACON)).lore(new String[] { "§7Current: " + (opener.getWorld().getSpawnLocation() != null ? new LocationFormat(opener.getWorld().getSpawnLocation()).format("x, y, z in world") : "§c-") }).build(), new Runnable() {

			@Override
			public void run() {
				opener.getWorld().setSpawnLocation(opener.getLocation().getBlockX(), opener.getLocation().getBlockY(), opener.getLocation().getBlockZ());
			}
		});
		return true;
	}
}