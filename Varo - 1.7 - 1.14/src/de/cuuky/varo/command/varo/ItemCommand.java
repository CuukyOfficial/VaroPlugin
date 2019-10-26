package de.cuuky.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.list.VaroList;
import de.cuuky.varo.list.item.ItemList;
import de.cuuky.varo.player.VaroPlayer;

public class ItemCommand extends VaroCommand {

	public ItemCommand() {
		super("item", "Blockt oder erlaubt Items", "varo.item", "itemlist");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Item §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Add <Anzahl>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Remove [All/Anzahl]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7list");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Tipp: §7Der /varo enchant Befehl blockt alle Enchantments, die auf deinem derzeitigen Item sind.");
			sender.sendMessage(Main.getPrefix());
			sender.sendMessage(Main.getPrefix() + "§7Dieser Command fügt die Sachen der Listen hinzu, die du in der Hand hältst.");
			sender.sendMessage(Main.getPrefix() + "§7-----------------");
			return;
		}

		if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(Main.getPrefix() + "Liste aller " + Main.getColorCode() + "Itemlisten§7:");
			for(VaroList list : ItemList.getLists())
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + list.getLocation());
			return;
		}

		if(args.length < 2) {
			sender.sendMessage(Main.getPrefix() + "Falsche Argumente! " + Main.getColorCode() + label + " item");
			return;
		}

		ItemList list = ItemList.getItemList(args[0]);

		if(list == null) {
			sender.sendMessage(Main.getPrefix() + "Liste " + args[0] + " nicht gefunden!");
			return;
		}

		if(args[1].equalsIgnoreCase("list")) {
			sender.sendMessage(Main.getPrefix() + "Liste aller Items von " + Main.getColorCode() + list.getLocation() + "§7:");
			for(ItemStack stack : list.getItems())
				sender.sendMessage(Main.getPrefix() + stack.toString());
			return;
		}

		Player player = vp.getPlayer();
		if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			sender.sendMessage(Main.getPrefix() + "Du hast kein Item in der Hand!");
			return;
		}

		ItemStack item = player.getItemInHand();
		if(args[1].equalsIgnoreCase("add")) {
			int Anzahl = 1;
			if (args.length > 2) {
				try {
					Anzahl = Integer.parseInt(args[2]);
				} catch(NumberFormatException e) {
					Anzahl = 1;
				}
			}
			ArrayList<ItemList> multipleAdd = ItemList.getItemListsMultipleAdd();
			if (multipleAdd.contains(list)) {
				for (int i = 0; i < Anzahl; i++) {
					list.addItem(item);
				}
				sender.sendMessage(Main.getPrefix() + "Item erfolgreich " + String.valueOf(Anzahl) + " mal zu " + list.getLocation() + " hinzugefügt!");
			} else {
				if (list.hasItem(item)) {
					sender.sendMessage(Main.getPrefix() + "Auf dieser Liste kann ein item nicht mehrmals stehen.\n" + Main.getPrefix() + "Das item steht bereits auf dieser Liste.");
					return;
				} else {
					sender.sendMessage(Main.getPrefix() + "Item erfolgreich zu " + list.getLocation() + " hinzugefügt!");
					list.addItem(item);
				}
			}

		} else if(args[1].equalsIgnoreCase("remove")) {			
			if (!list.hasItem(item)) {
				sender.sendMessage(Main.getPrefix() + "Item steht nicht auf dieser Liste!");
				return;
			}
			
			int Anzahl = 1;
			
			if (args.length > 2) {
				if (args[2].equalsIgnoreCase("All")) {
					while (list.hasItem(item)) {
						list.removeItem(item);
					}
					sender.sendMessage(Main.getPrefix() + "Item erfolgreich komplett von " + list.getLocation() + " entfernt!");
					return;
				} else {
					try {
						Anzahl = Integer.parseInt(args[2]);
					} catch(NumberFormatException e) {
						Anzahl = 1;
					}
				}
			}
			for (int i=0; i<Anzahl; i++) {
				if (!list.hasItem(item)) {
					sender.sendMessage(Main.getPrefix() + "Item steht nur " + String.valueOf(i) + " mal auf der Liste.\n" + Main.getPrefix() + "Item wurde komplett von der Liste entfernt.");
					return;
				}
				list.removeItem(item);
			}
			
			sender.sendMessage(Main.getPrefix() + "Item erfolgreich " + Anzahl + " mal von " + list.getLocation() + " entfernt!");
		} else {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Add <Anzahl>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Remove [All/Anzahl]");
		}
	}
}
