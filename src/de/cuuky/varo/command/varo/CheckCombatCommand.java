package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckCombatCommand extends VaroCommand {
    private static final String[] subCommands = null;
    public CheckCombatCommand() {
        super("checkcombat", "Überprüft ob du dich im Combat befindest", "varo.checkcombat", subCommands,  "combat", "combatlog", "cl", "cc", "cls", "combatlogstatus");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (vp == null) {
            sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
            return;
        }

        if (args.length != 0) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CHECKCOMBAT_HELP.getValue(vp));
            return;
        }

        if (new CombatlogCheck(vp.getPlayer()).isCombatLog()) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CHECKCOMBAT_INCOMBAT.getValue(vp));
        } else {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_CHECKCOMBAT_NOTINCOMBAT.getValue(vp));
        }
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 2 && subCommands != null) {
            List<String> subCommands = Arrays.asList(this.subCommands);
            list.addAll(subCommands);
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
