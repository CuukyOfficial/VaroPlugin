package de.cuuky.varo.gui.player;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.version.types.Materials;

public class PlayerListGUI extends SuperInventory {

	public enum PlayerGUIType {
		SPECTATOR("§fSPECTATOR", Materials.REDSTONE.parseMaterial()),
		DEAD("§4DEAD", Materials.SKELETON_SKULL_17.parseMaterial()),
		REGISTERED("§bREGISTERED", Material.BOOK),
		ALIVE("§aALIVE", Material.POTION),
		ONLINE("§eONLINE", Material.EMERALD);

		private String typeName;
		private Material icon;

		private PlayerGUIType(String typeName, Material icon) {
			this.typeName = typeName;
			this.icon = icon;
		}

		public Material getIcon() {
			return icon;
		}

		public String getTypeName() {
			return typeName;
		}

		public ArrayList<VaroPlayer> getList() {
			switch(this) {
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

		public static PlayerGUIType getType(String name) {
			for(PlayerGUIType type : values())
				if(type.getTypeName().equals(name))
					return type;

			return null;
		}
	}

	private boolean showStats;
	private PlayerGUIType type;

	public PlayerListGUI(Player opener, boolean showstats, PlayerGUIType type) {
		super("§cPlayer", opener, 45, false);

		this.showStats = showstats;
		this.type = type;
		open();
	}

	@Override
	public boolean onOpen() {
		ArrayList<VaroPlayer> list = type.getList();

		int start = getSize() * (getPage() - 1);
		for(int i = 0; i != getSize(); i++) {
			VaroPlayer players;
			try {
				players = list.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			linkItemTo(i, new ItemBuilder().playername(players.getName()).lore((showStats ? players.getStats().getStatsListed() : new String[] {})).buildSkull(), new Runnable() {

				@Override
				public void run() {
					if(!opener.hasPermission("varo.player"))
						return;

					new PlayerGUI(opener, players, type);
				}
			});
			start++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onBackClick() {
		new PlayerListChooseGUI(opener, showStats);
		return true;
	}
}
