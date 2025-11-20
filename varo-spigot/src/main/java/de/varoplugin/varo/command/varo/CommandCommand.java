package de.varoplugin.varo.command.varo;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.varoplugin.cfw.chat.PageableChatBuilder;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.command.VaroChatListMessages;
import de.varoplugin.varo.command.VaroCommand;
import de.varoplugin.varo.command.custom.CustomCommand;
import de.varoplugin.varo.command.custom.CustomCommandManager;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.gui.admin.customcommands.CreateCustomCommandGUI;
import de.varoplugin.varo.gui.admin.customcommands.CustomCommandMenuGUI;
import de.varoplugin.varo.player.VaroPlayer;

public class CommandCommand extends VaroCommand {

    private final PageableChatBuilder<CustomCommand> builder;

    public CommandCommand() {
        super("command", "Erstelle eigene Befehle", "varo.command");

        builder = new PageableChatBuilder<>(() -> Main.getDataManager().getCustomCommandManager().getCommands()).messages(new VaroChatListMessages<>(command -> {
            String str = "";
            String unused = (command.isUnused()) ? ChatColor.RED + "*" : "" ;
            str += Main.getPrefix() + " " + Main.getColorCode() + command.getName() + unused + ChatColor.GRAY + " (" + command.getPermission() + ")" + ChatColor.DARK_GRAY + ": " + command.getDescription() + "\n";
            str += Main.getPrefix() + "  " + ChatColor.GRAY + command.getOutput() + "\n";
            str += Main.getPrefix();
            return str;
        }, "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command list", "Varo Commands " + ChatColor.RED + "*deactivated"));
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            this.sendUsage(sender);
            return;
        }

        if (args.length == 1) {
            Main.getDataManager().getCustomCommandManager().getCommands().forEach(command -> {
                if (command.getName().equalsIgnoreCase(args[0])) {
                    new CreateCustomCommandGUI(vp.getPlayer(), command);
                }
            });
        }

        CustomCommandManager ccm = Main.getDataManager().getCustomCommandManager();
        String subCommand = args[0].toLowerCase();
        if (subCommand.equalsIgnoreCase("create")) {
            if (args.length < 2) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command create §7<Name>;<Output>;<Beschreibung>;<Permission>");
                return;
            }

            String compressed = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
            String[] arguments = compressed.replaceAll("\\s*;\\s*", ";").split(";");

            if (arguments.length != 4) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command create §7<Name>;<Output>;<Beschreibung>;<Permission>");
                return;
            }

            String name = arguments[0].toLowerCase();
            if (Main.getDataManager().getCustomCommandManager().isIllegalCommand(name)) {
                sender.sendMessage((Main.getPrefix() + ChatColor.RED + "Diesen Namen darfst du nicht verwenden!"));
                return;
            }

            String output = arguments[1], description = arguments[2],
                    permission = arguments[3].equalsIgnoreCase("null") ? null : arguments[3];

            CustomCommand command = new CustomCommand(name, output, description, permission, false);
            command.setConfigEntry();
            command.save();
            sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Command " + Main.getColorCode() + name + ChatColor.GRAY + " erfolreich mit dem output " + Main.getColorCode() + output + ChatColor.GRAY + " erstellt.");
        } else if (subCommand.equalsIgnoreCase("edit")) {
            if (args.length < 2) {
                this.sendEditUsage(sender);
                return;
            }
            String subSubCommand = args[1].toLowerCase();

            if (args.length < 4) {
                switch (subSubCommand) {
                    case "description": {
                        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit description <Name> <Neue Beschreibung>");
                        break;
                    }
                    case "output": {
                        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit output <Name> <Neuer Output>");
                        break;
                    }
                    case "permission": {
                        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit permission <Name> <Neue Permission>");
                        break;
                    }
                    case "name": {
                        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit name <Name> <Neuer Name>");
                        break;
                    }
                    case "deactivated": {
                        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit deactivated <Name> <true/false>");
                        break;
                    }
                    default: {
                        this.sendEditUsage(sender);
                        break;
                    }
                }
                return;
            }

            CustomCommand command = ccm.getCommand(args[2].toLowerCase());
            if (command == null) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Der Command, welchen du veraendern willst exestiert nicht!");
                return;
            }
            String name = command.getName();

            StringBuilder change = new StringBuilder(args[3]);
            if (subSubCommand.equalsIgnoreCase("description") || subSubCommand.equalsIgnoreCase("output"))
                for (int i = 4; i < args.length; i++)
                    change.append(" ").append(args[i]);

            if (subSubCommand.equalsIgnoreCase("description")) {
                command.editDescription(change.toString());
                command.save();
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Beschreibung des Commands " + Main.getColorCode() + name + ChatColor.GRAY + " erfolgreich zu " + Main.getColorCode() + change + ChatColor.GRAY + " geaendert.");
            } else if (subSubCommand.equalsIgnoreCase("name")) {
                String newName = args[3].toLowerCase();
                if (Main.getDataManager().getCustomCommandManager().isIllegalCommand(newName)) {
                    sender.sendMessage(Main.getPrefix() + ChatColor.RED + "Diesen Namen darfst du nicht verwenden!");
                    return;
                }

                command.renameCommand(newName);
                command.save();
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Command " + Main.getColorCode() + name + ChatColor.GRAY + " erfolreich zu " + Main.getColorCode() + newName + ChatColor.GRAY + " umbenannt.");
            } else if (subSubCommand.equalsIgnoreCase("output")) {
                command.editOutput(change.toString());
                command.save();
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Output des Commands " + Main.getColorCode() + name + ChatColor.GRAY + " erfolreich zu " + Main.getColorCode() + change + ChatColor.GRAY + " bearbeitet.");
            } else if (subSubCommand.equalsIgnoreCase("permission")) {
                String permission = args[3];

                command.editPermission(permission);
                command.save();
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Permission des Commands " + Main.getColorCode() + name + ChatColor.GRAY + " erfolreich zu " + Main.getColorCode() + permission + ChatColor.GRAY + " bearbeitet.");
            } else if (subSubCommand.equalsIgnoreCase("deactivated")) {
                String boo = args[3].toLowerCase();

                if (!boo.equals("true") && !boo.equals("false")) {
                    sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Bitte verwende \"true\" oder \"false\"!");
                    return;
                }

                command.setUnused(Boolean.parseBoolean(boo));
                command.save();
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Deactivated Feature des Commands " + Main.getColorCode() + name + ChatColor.GRAY + " erfolreich auf " + Main.getColorCode() + boo + ChatColor.GRAY + " gesetzt.");
            }
        } else if (subCommand.equalsIgnoreCase("help")) {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.BOLD + "Hilfe zu Custom commands:");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.GRAY + "- Du kannst die Commands, sowohl mit Commands\n" + Main.getPrefix() + "  als auch via GUI bearbeiten: " + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command menu");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.GRAY + "- Die Permission kannst du auf \"null\" setzen.");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.GRAY + "- Du kannst nicht mehr als einmal den gleichen\n" + Main.getPrefix() + "  Namen für einen Command benutzen.");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.GRAY + "- Ein Name darf keine Leerzeichen enthalten.");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.GRAY + "- Du kannst Custom Commands deaktivieren:\n" + Main.getPrefix() + Main.getColorCode() + "  /" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit deactivated <Name> <true/false>");
            sender.sendMessage(Main.getPrefix());
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + ChatColor.GRAY + "- Du kannst die Commands auch in der Datei der config\n" + Main.getPrefix() + "  verändern. Dabei sollten keine Umlaute (ä, ö, ü)\n" + Main.getPrefix() + "  benutzt werden. Die Änderungen, die du dort triffst\n" + Main.getPrefix() + "  kannst du mit " + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " config reload" + ChatColor.GRAY + " laden.");
        } else if (subCommand.equalsIgnoreCase("info")) {
            if (args.length < 2) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command info <Name>");
                return;
            }

            CustomCommand command = ccm.getCommand(args[1].toLowerCase());
            if (command == null) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Dieser Command exestiert nicht!");
                return;
            }

            String name = command.getName(), output = command.getOutput(), description = command.getDescription(), permission = command.getPermission();

            sender.sendMessage(Main.getPrefix() + ChatColor.BOLD + "Command Info:");
            if (command.isUnused()) sender.sendMessage(Main.getPrefix() + " " + ChatColor.RED + "Deactivated!");
            sender.sendMessage(Main.getPrefix() + " " + Main.getColorCode() + name + ChatColor.GRAY + " (" + permission + ")" + ChatColor.DARK_GRAY + ": " + description);
            sender.sendMessage(Main.getPrefix() + "  " + ChatColor.GRAY + output);
        } else if (subCommand.equalsIgnoreCase("list")) {
            try {
                this.builder.page(args.length > 1 ? args[1] : "1").build().send(sender);
            } catch (NumberFormatException e) {
                this.sendUsage(sender);
            }
        } else if (subCommand.equalsIgnoreCase("menu")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getConsolePrefix() + "Du musst ein Spieler sein!");
                return;
            }

            new CustomCommandMenuGUI((Player) sender);
        } else if (subCommand.equalsIgnoreCase("remove") || subCommand.equalsIgnoreCase("delete")) {
            if (args.length < 2) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command remove <Name>");
                return;
            }

            CustomCommand command = ccm.getCommand(args[1].toLowerCase());
            if (command == null) {
                sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Dieser Command exestiert nicht!");
                return;
            }

            command.removeCommand();
            sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "Command " + Main.getColorCode() + command.getName() + ChatColor.GRAY + " erfolgreich entfernt.");
        } else this.sendUsage(sender);
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Command Befehle:");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command §7<Name>");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command create §7<Name>;<Output>;<Beschreibung>;<Permission>");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit §7<description/name/output/deactivated/permission>");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command help");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command info §7<Name>");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command list");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command menu");
        sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command remove §7<Name>");
    }

    private void sendEditUsage(CommandSender sender) {
        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit description <Name> <Neue Beschreibung>");
        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit name <Name> <Neuer Name>");
        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit output <Name> <Neuer Output>");
        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit deactivated <Name> <true/false>");
        sender.sendMessage(Main.getPrefix() + ChatColor.GRAY + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " command edit permission <Name> <Neue Permission>");
    }
}
