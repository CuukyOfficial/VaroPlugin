package de.cuuky.varo.gui.admin.alert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.gui.SuperInventory;
import de.cuuky.varo.gui.utils.PageAction;
import de.cuuky.varo.item.ItemBuilder;
import de.cuuky.varo.version.types.Materials;

public class AlertChooseGUI extends SuperInventory {

	public enum AlertGUIType {
		ALL("§fALL", Material.BOOK),
		CLOSED("§4CLOSED", Materials.SKELETON_SKULL.parseMaterial()),
		OPEN("§eOPENED", Material.EMERALD);

		private Material icon;
		private String typeName;

		private AlertGUIType(String typeName, Material icon) {
			this.typeName = typeName;
			this.icon = icon;
		}

		public Material getIcon() {
			return icon;
		}

		public ArrayList<Alert> getList() {
			switch(this) {
			case ALL:
				return Alert.getAlerts();
			case CLOSED:
				return Alert.getClosedAlerts();
			case OPEN:
				return Alert.getOpenAlerts();
			}

			return null;
		}

		public String getTypeName() {
			return typeName;
		}

		public static AlertGUIType getType(String name) {
			for(AlertGUIType type : values())
				if(type.getTypeName().equals(name))
					return type;

			return null;
		}
	}

	private AlertGUIType type;

	public AlertChooseGUI(Player opener, AlertGUIType type) {
		super("§4Alerts", opener, 45, false);

		this.type = type;

		open();
	}

	@Override
	public boolean onBackClick() {
		new AlertTypeChooseGUI(opener);
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
		ArrayList<Alert> alerts = type.getList();
		Collections.reverse(alerts);

		int start = getSize() * (getPage() - 1);
		if(start != 0)
			start -= 2;

		for(int i = 0; i != getSize() - 2; i++) {
			Alert alert;
			try {
				alert = alerts.get(start);
			} catch(IndexOutOfBoundsException e) {
				break;
			}

			linkItemTo(i, new ItemBuilder().displayname("§c" + alert.getType() + " §8| §7" + alert.getId()).itemstack(new ItemStack(Material.BOOK)).lore(new String[] { "§7Message: §f" + alert.getMessage(), "§7Date: §f" + new SimpleDateFormat("dd.MM.yyy HH:mm:ss").format(alert.getCreated()), "§7Open: §f" + alert.isOpen() }).build(), new Runnable() {

				@Override
				public void run() {
					new AlertOptionGUI(opener, alert, type);
				}
			});
			start++;
		}

		linkItemTo(getSize() - 1, new ItemBuilder().displayname("§cClose all").itemstack(new ItemStack(Materials.REDSTONE.parseMaterial())).build(), new Runnable() {

			@Override
			public void run() {
				for(Alert alert : alerts)
					alert.setOpen(false);

				updateInventory();
			}
		});

		return calculatePages(alerts.size(), getSize()) == getPage();
	}
}
