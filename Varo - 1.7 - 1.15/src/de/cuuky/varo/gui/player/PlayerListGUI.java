package de.cuuky.varo.gui.player;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.VaroSuperInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListGUI extends VaroSuperInventory {

	public enum PlayerGUIType {
		ALIVE("§aALIVE", Material.POTION),
		DEAD("§4DEAD", Materials.SKELETON_SKULL_17.parseMaterial()),
		ONLINE("§eONLINE", Material.EMERALD),
		REGISTERED("§bREGISTERED", Material.BOOK),
		SPECTATOR("§fSPECTATOR", Materials.REDSTONE.parseMaterial());

		private Material icon;
		private String typeName;

		private PlayerGUIType(String typeName, Material icon) {
			this.typeName = typeName;
			this.icon = icon;
		}

		public Material getIcon() {
			return icon;
		}

		public ArrayList<VaroPlayer> getList() {
			switch (this) {
			case SPECTATOR:
				return VaroPlayer.getSpectator();
			case DEAD:
				return VaroPlayer.getDeadPlayer();
			case REGISTERED:
				return VaroPlayer.getVaroPlayer();
			case ALIVE:
				return VaroPlayer.getAlivePlayer();
			case ONLINE:
				return VaroPlayer.getOnlinePlayer();
			}

			return null;
		}

		public String getTypeName() {
			return typeName;
		}

		public static PlayerGUIType getType(String name) {
			for (PlayerGUIType type : values())
				if (type.getTypeName().equals(name))
					return type;

			return null;
		}
	}

	private boolean showStats;

	private PlayerGUIType type;

	public PlayerListGUI(Player opener, PlayerGUIType type) {
		super("§cPlayer", opener, 54, false);

		this.showStats = opener.hasPermission("varo.setup");
		this.type = type;
		this.setModifier = true;
		this.animations = false;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new PlayerListChooseGUI(opener);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		ArrayList<VaroPlayer> list = type.getList();

		int start = getSize() * (getPage() - 1);
		for (int i = 0; i != getSize(); i++) {
			VaroPlayer players;
			try {
				players = list.get(start);
			} catch (IndexOutOfBoundsException e) {
				break;
			}

			int finalI = i;
			new BukkitRunnable() {
				@Override
				public void run() {
					linkItemTo(finalI, new ItemBuilder().playername(players.getName()).lore((showStats ? players.getStats().getStatsListed() : new String[] {})).buildSkull(), () -> {
						if (!opener.hasPermission("varo.player"))
							return;

						new PlayerGUI(opener, players, type);
					});
				}
			}.runTaskAsynchronously(Main.getInstance());
			start++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}
}