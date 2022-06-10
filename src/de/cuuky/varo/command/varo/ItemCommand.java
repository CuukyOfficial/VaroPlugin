package de.cuuky.varo.command.varo;

import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.list.enchantment.EnchantmentList;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.gui.items.ItemListInventory;
import de.cuuky.varo.list.VaroList;
import de.cuuky.varo.list.item.ItemList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemCommand extends VaroCommand {
    private static final String[] subCommands = {"list"};
    public ItemCommand() {
        super("item", "Blockt oder erlaubt Items", "varo.item", subCommands, "itemlist", "items");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (vp == null) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Item §7-----");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> - Öffnet Inventar");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Add");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Remove");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> list");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7list");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Tipp: §7Der /" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " enchant Befehl blockt alle Enchantments, die auf deinem derzeitigen Item sind.");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + "§7Dieser Command fuegt die Sachen der Listen hinzu, die du in der Hand haeltst.");
            sender.sendMessage(Main.getPrefix() + "§7-----------------");
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(Main.getPrefix() + "Liste aller " + Main.getColorCode() + "Itemlisten§7:");
            for (VaroList list : ItemList.getItemLists())
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + list.getLocation());
            return;
        }

        ItemList list = ItemList.getItemList(args[0]);
        if (list == null) {
            sender.sendMessage(Main.getPrefix() + "Liste " + args[0] + " nicht gefunden!");
            return;
        }

        Player player = vp.getPlayer();
        if (args.length == 1) {
            new ItemListInventory(player, list);
            return;
        }

        if (args[1].equalsIgnoreCase("list")) {
            if (list.getItems().size() < 1) {
                sender.sendMessage(Main.getPrefix() + "Keine Items gefunden!");
                return;
            }

            sender.sendMessage(Main.getPrefix() + "Liste aller Items von " + Main.getColorCode() + list.getLocation() + "§7:");
            for (ItemStack stack : list.getItems()) sender.sendMessage(Main.getPrefix() + stack.toString());
            return;
        }

        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.AIR) {
            sender.sendMessage(Main.getPrefix() + "Du hast kein Item in der Hand!");
            return;
        }

        if (args[1].equalsIgnoreCase("add")) {
            if (list.isUniqueType() && list.hasItem(item)) {
                sender.sendMessage(Main.getPrefix() + "Auf dieser Liste kann ein item nicht mehrmals stehen.\n" + Main.getPrefix() + "Das item steht bereits auf dieser Liste.");
                return;
            }

            list.addItem(item);
            list.saveList();
            sender.sendMessage(Main.getPrefix() + "Item erfolgreich zu " + list.getLocation() + " hinzugefuegt!");
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (!list.hasItem(item)) {
                sender.sendMessage(Main.getPrefix() + "Item steht nicht auf dieser Liste!");
                return;
            }

            while (list.hasItem(item)) list.removeItem(item);
            list.saveList();
            sender.sendMessage(Main.getPrefix() + "Item erfolgreich komplett von " + list.getLocation() + " entfernt!");
        } else {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Add <Anzahl>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " item §7<itemlist> Remove [@a/Anzahl]");
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 2 && subCommands != null) {
            List<String> subCommands = Arrays.asList(this.subCommands);
            list.addAll(subCommands);
            for (ItemList it : ItemList.getItemLists()) {
                list.add(it.getLocation());
            }
        }
        if (args.length == 3) {
            String[] subCommands = {"Add", "Remove", "List"};
            List<String> subCommandsList = Arrays.asList(subCommands);
            list.addAll(subCommandsList);
        }
        ArrayList<String> completerList = new ArrayList<>();
        String curentarg = args[args.length - 1].toLowerCase();
        for (String s : list) {
            String s1 = s.toLowerCase();
            if (s1.startsWith(curentarg)) {
                completerList.add(s);
            }
        }
        return completerList;
    }
}