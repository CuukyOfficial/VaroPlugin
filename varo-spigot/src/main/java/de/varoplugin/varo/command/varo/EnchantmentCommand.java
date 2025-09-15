package de.varoplugin.varo.command.varo;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.player.VaroPlayer;

public class EnchantmentCommand extends VaroCommand {

	public EnchantmentCommand() {
		super("enchantment", "Einstellungen zu den blockierten Verzauberungen", "varo.enchantment", "enchantments", "enchant", "enchants");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (vp == null) {
			Messages.COMMANDS_ERROR_NO_CONSOLE.send(sender);
			return;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Enchantments §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " enchantment §7[Remove / Add / List]");
			sender.sendMessage(Main.getPrefix());
			sender.sendMessage(Main.getPrefix() + "§7Dieser Command blockiert alle Verzauberungungen des Items das du in der Hand hältst.");
			sender.sendMessage(Main.getPrefix() + "§7Alternativ sind auch Buecher möglich");
			sender.sendMessage(Main.getPrefix() + "§7--------------------");
			return;
		}

		List<String> list = VaroConfig.ENCHANTMENT_BLOCKED.getValue();
		if (args[0].equalsIgnoreCase("list")) {
			if (list.isEmpty()) {
				sender.sendMessage(Main.getPrefix() + "Keine geblockten Verzauberungen gefunden!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "Liste aller geblockten Verzauberungen§7:");
			for (String enchantment : list)
				sender.sendMessage(Main.getPrefix() + enchantment);
			return;
		}

		Player player = vp.getPlayer();
		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			sender.sendMessage(Main.getPrefix() + "Du hast kein Item in der Hand!");
			return;
		}

		ItemStack item = player.getItemInHand();
		Map<Enchantment, Integer> encs;
		if (item.getItemMeta() instanceof EnchantmentStorageMeta) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
			encs = meta.getStoredEnchants();
		} else
			encs = item.getItemMeta().getEnchants();

		if (encs.size() == 0) {
			sender.sendMessage(Main.getPrefix() + "Es wurden keine Enchantments auf deinem Item/Buch gefunden!");
			return;
		}

		if (args[0].equalsIgnoreCase("add")) {
		    Main.getDataManager().getEnchantmentManager().blockEnchantments(encs.keySet().toArray(new Enchantment[0]));
		    sender.sendMessage(Main.getPrefix() + "Verzauberungen erfolgreich blockiert!");
		} else if (args[0].equalsIgnoreCase("remove")) {
			Main.getDataManager().getEnchantmentManager().unblockEnchantments(encs.keySet().toArray(new Enchantment[0]));
            sender.sendMessage(Main.getPrefix() + "Verzauberungen erfolgreich freigegeben!");
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " enchantment §7[Remove / Add / List]");
	}
}