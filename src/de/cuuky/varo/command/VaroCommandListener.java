package de.cuuky.varo.command;

import de.cuuky.varo.command.custom.CustomCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroCommandListener implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        VaroPlayer player = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
        if (args.length < 1) {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lVaro §7§lCommands:");
            for (VaroCommand command : VaroCommand.getVaroCommand())
                if ((command.getPermission() == null || sender.hasPermission(command.getPermission())) && (command instanceof CustomCommand) ? !((CustomCommand) command).isUnused() : true)
                    sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " " + command.getName() + "§8: §7" + command.getDescription());
            return false;
        }

        VaroCommand command = VaroCommand.getCommand(args[0]);
        if (command == null) {
            sender.sendMessage(Main.getPrefix() + "§7Kommando '" + Main.getColorCode() + args[0] + "§7' nicht gefunden!");
            return false;
        }

        if (command instanceof CustomCommand && ((CustomCommand) command).isUnused()) {
            if (sender.hasPermission("varo.useCustoms")) {
                sender.sendMessage(player == null ? Main.getConsolePrefix() : Main.getPrefix() + ChatColor.GRAY + "Dieser Command ist " + Main.getColorCode() + "deaktiviert" + ChatColor.GRAY + ",  aber du kannst ihn\n" + Main.getPrefix() + "benutzen:");
                command.onCommand(sender, player, cmd, label, JavaUtils.removeString(args, 0));
            } else
                sender.sendMessage(Main.getPrefix() + "§7Kommando '" + Main.getColorCode() + args[0] + "§7' nicht gefunden!");
            return false;
        }

        if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
            sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(player, player));
            return false;
        }

        command.onCommand(sender, player, cmd, label, JavaUtils.removeString(args, 0));
        return true;
    }
}