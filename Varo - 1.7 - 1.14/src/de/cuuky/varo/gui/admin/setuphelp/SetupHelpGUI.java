package de.cuuky.varo.gui.admin.setuphelp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.admin.AdminMainMenu;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class SetupHelpGUI extends SuperInventory {

	public SetupHelpGUI(Player opener) {
		super("§eSetup Assistant", opener, 9, false);

		open();
	}

	@Override
	public boolean onOpen() {
		for (int i = 0; i < SetupCheckList.values().length; i++) {
			SetupCheckList check = SetupCheckList.values()[i];

			linkItemTo(i, new ItemBuilder().displayname(Main.getColorCode() + check.getName()).itemstack(new ItemStack(check.isChecked() ? Materials.GUNPOWDER.parseMaterial() : check.getIcon())).lore(check.getDescription()).build(), new Runnable() {

				@Override
				public void run() {
					check.setChecked(!check.isChecked());
				}
			});
		}

		return true;
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		updateInventory();
	}

	@Override
	public void onInventoryAction(PageAction action) {
	}

	@Override
	public boolean onBackClick() {
		new AdminMainMenu(opener);
		return true;
	}

	@Override
	public void onClose(InventoryCloseEvent event) {
	}

	public enum SetupCheckList {
		CONFIG_SETUP("Config Setup", "Haben sie die Config augesetzt? (GUI)", Materials.SIGN.parseMaterial()),
		TEAM_SETUP("Team Setup", "Haben sie alle Teams oder Spieler eingetragen? /varo team", Material.DIAMOND_HELMET),
		SPAWN_SETUP("Spawn Setup", "Haben sie die Spawns gesetzt? /varo spawns", Materials.OAK_SLAB.parseMaterial()),
		WORLDSPAWN_SETUP("Worlspawn Setup", "Haben sie den Worldspawn in der Mitte\ngesetzt? /setworldspawn", Material.BEACON),
		LOBBY_SETUP("Lobby Setup", "Haben sie die Lobby gesetzt? (GUI) (/Lobby)", Material.DIAMOND),
		PORTAL_SETUP("Portal Setup", "Haben sie das Portal gesetzt?", Material.OBSIDIAN),
		SCOREBOARD_SETUP("Scoreboard Setup", "Haben sie das Scoreboard aufgesetzt?", Material.REDSTONE),
		BORDER_SETUP("Border Setup", "Haben sie die Border entsprechend gesetzt?", Material.STICK),
		DISCORD_SETUP("Discord Setup", "Haben sie den DiscordBot aufgesetzt?", Material.ANVIL);

		private String name;
		private String[] description;
		private Material icon;
		private boolean checked;

		private SetupCheckList(String name, String description, Material icon) {
			this.name = name;
			this.description = description.split("\n");
			this.icon = icon;
			this.checked = false;
		}

		public Material getIcon() {
			return icon;
		}

		public String getName() {
			return name;
		}

		public String[] getDescription() {
			return description;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}
}
