package de.cuuky.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class FinaleCommand extends VaroCommand {

    public FinaleCommand() {
        super("finale", "Hauptcommand fuer das Managen des Finales", "varo.finale");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0 || (!args[0].equalsIgnoreCase("joinstart") && !args[0].equalsIgnoreCase("hauptstart") && !args[0].equalsIgnoreCase("abort") && !args[0].equalsIgnoreCase("abbruch") && !!args[0].equalsIgnoreCase("abbrechen") && !!args[0].equalsIgnoreCase("stop"))) {
            sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Finale Befehle:");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale joinStart");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale hauptStart [Countdown]");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale abort");
            return;
        }

        if (args[0].equalsIgnoreCase("joinstart")) {
            if (Main.getVaroGame().isFinale()) {
                sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
                return;
            }

            if (Main.getVaroGame().isFinaleCountdown()) {
                sender.sendMessage(Main.getPrefix() + "Der Finale-Countdown läuft bereits.");
                return;
            }

            if (Main.getVaroGame().isFinaleJoin()) {
                sender.sendMessage(Main.getPrefix() + "Der JoinStart wurde bereits aktiviert.");
                return;
            }

            Main.getVaroGame().startFinaleJoin();

            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es koennen nun alle zum Finale joinen.");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Es wird empfohlen, mindestens 5 Minuten zu warten, bis das Finale gestartet wird.");
            sender.sendMessage(Main.getPrefix() + "§c§lWARNUNG: §cBeim Starten mit §7§l/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale hauptStart§7 werden alle Spieler, die nicht online sind, getötet.");

            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Man kann nun zum Finale joinen!");
            return;
        } else if (args[0].equalsIgnoreCase("hauptstart") || args[0].equalsIgnoreCase("mainstart")) {
            if (Main.getVaroGame().isFinale()) {
                sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
                return;
            }

            if (Main.getVaroGame().isFinaleCountdown()) {
                sender.sendMessage(Main.getPrefix() + "Der Finale-Countdown läuft bereits.");
                return;
            }

            int countdown = 0;
            if (args.length == 1) {
                sender.sendMessage(Main.getPrefix() + "§c§lWARNUNG: §cBeim Start werden alle Spieler die nicht online sind getötet. Zum Abbrechen nutze §7§l/"  + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " finale abort");
                countdown = 120;
            } else if (args.length == 2) {
                try {
                    countdown = Integer.parseUnsignedInt(args[1]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(Main.getPrefix() + "§cUngültiger Countdown!");
                    return;
                }
            } else {
                sender.sendMessage(Main.getPrefix() + "§cUngültige Argumente!");
                return;
            }

            Main.getVaroGame().startFinaleCountdown(countdown);
            return;
        } else if (args[0].equalsIgnoreCase("abort") || args[0].equalsIgnoreCase("abbruch") || args[0].equalsIgnoreCase("abbrechen") || args[0].equalsIgnoreCase("stop")) {
            if (Main.getVaroGame().isFinale()) {
                sender.sendMessage(Main.getPrefix() + "Das Finale wurde bereits gestartet.");
                return;
            }

            if (!Main.getVaroGame().isFinaleCountdown()) {
                sender.sendMessage(Main.getPrefix() + "Es gibt keinen Countdown zum Abbrechen.");
                return;
            }

            Main.getVaroGame().abortFinaleStart();
            Bukkit.broadcastMessage("§7Der Finale-Start wurde §cabgebrochen§7!");
        }
    }
}