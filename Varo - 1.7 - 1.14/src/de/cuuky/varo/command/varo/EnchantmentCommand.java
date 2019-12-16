package de.cuuky.varo.command.varo;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.list.enchantment.EnchantmentList;

public class EnchantmentCommand extends VaroCommand {

	public EnchantmentCommand() {
		super("enchantment", "Einstellungen zu den Verzauberungslisten", "varo.enchantment", "enchantments", "enchant", "enchants");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
			return;
		}

		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Enchantments §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " enchantment §7<enchantmentlist> [Remove / Add / List]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7list");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Tipp: §7Der /varo item Befehl blockt alle Items.");
			sender.sendMessage(Main.getPrefix());
			sender.sendMessage(Main.getPrefix() + "§7Dieser Command fügt alle Verzauberungungen des Items, das du in der Hand hältst, der Liste hinzu.");
			sender.sendMessage(Main.getPrefix() + "§7--------------------");
			return;
		}

		if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			sender.sendMessage(Main.getPrefix() + "Liste aller " + Main.getColorCode() + "Enchantmentlisten§7:");
			for (EnchantmentList list : EnchantmentList.getEnchantmentLists())
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + list.getLocation());
			return;
		}

		if (args.length < 2) {
			sender.sendMessage(Main.getPrefix() + "Falsche Argumente! " + Main.getColorCode() + label + " enchant");
			return;
		}

		EnchantmentList list = EnchantmentList.getEnchantmentList(args[0]);
		if (list == null) {
			sender.sendMessage(Main.getPrefix() + "Liste " + args[0] + " nicht gefunden!");
			return;
		}

		if (args[1].equalsIgnoreCase("list")) {
			sender.sendMessage(Main.getPrefix() + "Liste aller Verzauberungen von " + Main.getColorCode() + list.getLocation() + "§7:");
			for (String enc1 : list.getEnchantments())
				sender.sendMessage(Main.getPrefix() + enc1);
			return;
		}

		Player player = vp.getPlayer();
		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			sender.sendMessage(Main.getPrefix() + "Du hast kein Item in der Hand!");
			return;
		}

		ItemStack item = player.getItemInHand();
		Map<Enchantment, Integer> encs = item.getEnchantments();
		for (Enchantment enc : encs.keySet()) {
			if (args[1].contains("add")) {
				if (list.hasEnchantment(enc, encs.get(enc))) {
					sender.sendMessage(Main.getPrefix() + "Verzauberung '" + enc.getName() + " (" + encs.get(enc) + ")' steht bereits auf dieser Liste!");
					return;
				}

				list.addEnchantment(enc, encs.get(enc));
				sender.sendMessage(Main.getPrefix() + "Verzauberung " + enc.getName() + " (" + encs.get(enc) + ") erfolgreich zu " + list.getLocation() + " hinzugefügt!");
			} else if (args[1].equalsIgnoreCase("remove")) {
				if (!list.hasEnchantment(enc, encs.get(enc))) {
					sender.sendMessage(Main.getPrefix() + "Verzauberung '" + enc.getName() + " (" + encs.get(enc) + ")' steht nicht auf dieser Liste!");
					return;
				}

				list.removeEnchantment(enc, encs.get(enc));
				sender.sendMessage(Main.getPrefix() + "Verzauberung " + enc.getName() + " (" + encs.get(enc) + ") erfolgreich von " + list.getLocation() + " entfernt!");
			} else if (args[1].equalsIgnoreCase("list")) {
				sender.sendMessage(Main.getPrefix() + "Liste aller Verzauberungen von " + Main.getColorCode() + list.getLocation() + "§7:");
				for (String enc1 : list.getEnchantments())
					sender.sendMessage(Main.getPrefix() + enc1);
			} else
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " enchantment §7<enchantmentlist> [Remove / Add / List]");
		}
	}
}