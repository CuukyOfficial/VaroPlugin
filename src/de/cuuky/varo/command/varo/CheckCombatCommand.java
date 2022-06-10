package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

import java.util.ArrayList;
import java.util.List;

public class CheckCombatCommand extends VaroCommand {
    public CheckCombatCommand() {
        super("checkcombat", "Überprüft ob du dich im Combat befindest", "varo.checkcombat", null,  "combat", "combatlog", "cl", "cc", "cls", "combatlogstatus");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (vp == null) {
            sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_CONSOLE.getValue(vp));
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
        return new ArrayList<>();
    }
}
