package de.varoplugin.varo.command;

import java.util.Arrays;

import io.github.almightysatan.slams.Placeholder;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.custom.CustomCommand;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;

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
            Messages.COMMANDS_ERROR_UNKNOWN_COMMAND.send(sender, Placeholder.constant("command", args[0]));
            return false;
        }

        if (command instanceof CustomCommand && ((CustomCommand) command).isUnused()) {
            if (sender.hasPermission("varo.useCustoms")) {
                sender.sendMessage(player == null ? Main.getConsolePrefix() : Main.getPrefix() + ChatColor.GRAY + "Dieser Command ist " + Main.getColorCode() + "deaktiviert" + ChatColor.GRAY + ",  aber du kannst ihn\n" + Main.getPrefix() + "benutzen:");
                command.onCommand(sender, player, cmd, label, Arrays.copyOfRange(args, 1, args.length));
            } else
                Messages.COMMANDS_ERROR_UNKNOWN_COMMAND.send(sender, Placeholder.constant("command", args[0]));
            return false;
        }

        if (command.getPermission() != null && !sender.hasPermission(command.getPermission())) {
            Messages.COMMANDS_ERROR_PERMISSION.send(sender);
            return false;
        }

        command.onCommand(sender, player, cmd, label, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }
}