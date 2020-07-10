package de.cuuky.varo.gui.team;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.cfw.item.ItemBuilder;
import de.cuuky.cfw.menu.SuperInventory;
import de.cuuky.cfw.menu.utils.PageAction;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.team.VaroTeam;

public class TeamListGUI extends SuperInventory {

	public enum TeamGUIType {
		ALIVE("§aALIVE", Material.POTION),
		DEAD("§4DEAD", Materials.REDSTONE.parseMaterial()),
		ONLINE("§eONLINE", Material.EMERALD),
		REGISTERED("§bREGISTERED", Material.BOOK);

		private Material icon;
		private String typeName;

		private TeamGUIType(String typeName, Material icon) {
			this.typeName = typeName;
			this.icon = icon;
		}

		public Material getIcon() {
			return icon;
		}

		public ArrayList<VaroTeam> getList() {
			switch (this) {
			case DEAD:
				return VaroTeam.getDeadTeams();
			case REGISTERED:
				return VaroTeam.getTeams();
			case ALIVE:
				return VaroTeam.getAliveTeams();
			case ONLINE:
				return VaroTeam.getOnlineTeams();
			}

			return null;
		}

		public String getTypeName() {
			return typeName;
		}
	}

	private TeamGUIType type;

	public TeamListGUI(Player opener, TeamGUIType type) {
		super("§7Choose Team", opener, 54, false);

		this.type = type;
		this.setModifier = true;
		Main.getCuukyFrameWork().getInventoryManager().registerInventory(this);
		open();
	}

	@Override
	public boolean onBackClick() {
		new TeamChooseGUI(opener);
		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

	@Override
	public void onClose(InventoryCloseEvent event) {

	}

	@Override
	public void onInventoryAction(PageAction action) {}

	@Override
	public boolean onOpen() {
		ArrayList<VaroTeam> list = type.getList();

		int start = getSize() * (getPage() - 1);
		for (int i = 0; i != getSize(); i++) {
			VaroTeam team;
			try {
				team = list.get(start);
			} catch (IndexOutOfBoundsException e) {
				break;
			}

			linkItemTo(i, new ItemBuilder().displayname(team.getDisplay()).material(Material.DIAMOND_HELMET).build(), new Runnable() {

				@Override
				public void run() {
					if (!opener.hasPermission("varo.admin")) {
						return;
					}

					new TeamGUI(opener, team);
				}
			});
			start++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}
}
