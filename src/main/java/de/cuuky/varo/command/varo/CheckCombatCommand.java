package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.combatlog.CombatlogCheck;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.player.VaroPlayer;

public class CheckCombatCommand extends VaroCommand {

    public CheckCombatCommand() {
        super("checkcombat", "Überprüft ob du dich im Combat befindest", "varo.checkcombat", "combat", "combatlog", "cl", "cc", "cls", "combatlogstatus");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (vp == null) {
        	Messages.COMMANDS_ERROR_NO_CONSOLE.send(vp);
            return;
        }

        if (args.length != 0) {
            Messages.COMMANDS_VARO_CHECKCOMBAT_HELP.send(vp);
            return;
        }

        if (new CombatlogCheck(vp.getPlayer()).isCombatLog()) {
            Messages.COMMANDS_VARO_CHECKCOMBAT_INCOMBAT.send(vp);
        } else {
            Messages.COMMANDS_VARO_CHECKCOMBAT_NOTINCOMBAT.send(vp);
        }
    }

}
