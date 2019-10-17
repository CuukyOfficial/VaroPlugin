package de.cuuky.varo.gui.team;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.team.Team;
import de.cuuky.varo.version.types.Materials;

public class TeamListGUI extends SuperInventory {

	public enum TeamGUIType {
		DEAD("§4DEAD", Materials.REDSTONE.parseMaterial()), REGISTERED("§bREGISTERED", Material.BOOK),
		ALIVE("§aALIVE", Material.POTION), ONLINE("§eONLINE", Material.EMERALD);

		private String typeName;
		private Material icon;

		private TeamGUIType(String typeName, Material icon) {
			this.typeName = typeName;
			this.icon = icon;
		}

		public Material getIcon() {
			return icon;
		}

		public String getTypeName() {
			return typeName;
		}

		public ArrayList<Team> getList() {
			switch (this) {
			case DEAD:
				return Team.getDeadTeams();
			case REGISTERED:
				return Team.getTeams();
			case ALIVE:
				return Team.getAliveTeams();
			case ONLINE:
				return Team.getOnlineTeams();
			}

			return null;
		}
	}

	private TeamGUIType type;

	public TeamListGUI(Player opener, TeamGUIType type) {
		super("§7Choose Team", opener, 45, false);

		this.type = type;
		open();
	}

	@Override
	public boolean onOpen() {
		ArrayList<Team> list = type.getList();

		int start = getSize() * (getPage() - 1);
		for (int i = 0; i != getSize(); i++) {
			Team team;
			try {
				team = list.get(start);
			} catch (IndexOutOfBoundsException e) {
				break;
			}

			linkItemTo(i, new ItemBuilder().displayname(team.getDisplay()).playername(team.getMember().get(0).getName())
					.buildSkull(), new Runnable() {

						@Override
						public void run() {
							new TeamGUI(opener, team);
						}
					});
			start++;
		}

		return calculatePages(list.size(), getSize()) == page;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
	}

	@Override
	public void onInventoryAction(PageAction action) {
	}

	@Override
	public boolean onBackClick() {
		new TeamChooseGUI(opener);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {

	}
}
