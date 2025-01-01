package de.cuuky.varo.command.varo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;

public class AutoStartCommand extends VaroCommand {

	/*
	 * OLD CODE
	 */

	public AutoStartCommand() {
		super("autostart", "Startet das Varo automatisch", "varo.autostart", "as");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (Main.getVaroGame().hasStarted()) {
			Messages.COMMANDS_VARO_AUTOSTART_ALREADY_STARTED.send(sender);
			return;
		}

		if (args.length == 0) {
		    Messages.CATEGORY_HEADER.send(sender, Placeholder.constant("category", "Autostart"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " autostart §7info");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " autostart §7set <Hour> <Minute> <Day> <Month> <Year>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " autostart §7remove");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " autostart §7delay <Minutes>");
			Messages.CATEGORY_FOOTER.send(sender, Placeholder.constant("category", "Autostart"));
			return;
		}

		if (args[0].equalsIgnoreCase("set")) {
			if (Main.getVaroGame().getAutoStart() != null) {
			    Messages.COMMANDS_VARO_AUTOSTART_ALREADY_SETUP.send(sender);
				return;
			}

			if (args.length != 6) {
			    Messages.COMMANDS_VARO_AUTOSTART_HELP_SET.send(sender);
				return;
			}

			if (args[5].length() == 2)
				args[5] = 20 + args[5];

			int min, hour, day, month, year;
			try {
				min = Integer.parseInt(args[2]);
				hour = Integer.parseInt(args[1]);
				day = Integer.parseInt(args[3]);
				month = Integer.parseInt(args[4]) - 1;
				year = Integer.parseInt(args[5]);
			} catch (NumberFormatException e) {
				Messages.COMMANDS_VARO_AUTOSTART_NO_NUMBER.send(sender);
				return;
			}

			Calendar start = new GregorianCalendar(year, month, day, hour, min, 0);
			if (new GregorianCalendar().after(start)) {
				Messages.COMMANDS_VARO_AUTOSTART_DATE_IN_THE_PAST.send(sender);
				return;
			}

			Main.getVaroGame().setAutoStart(new AutoStart(start));
			return;
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (Main.getVaroGame().getAutoStart() == null) {
				Messages.COMMANDS_VARO_AUTOSTART_NOT_SETUP_YET.send(sender);
				return;
			}

			Main.getVaroGame().getAutoStart().stop();
			Messages.COMMANDS_VARO_AUTOSTART_REMOVED.send(sender);
		} else if (args[0].equalsIgnoreCase("delay")) {
			if (Main.getVaroGame().getAutoStart() == null) {
				Messages.COMMANDS_VARO_AUTOSTART_NOT_SETUP_YET.send(sender);
				return;
			}

			if (args.length < 2) {
				Messages.COMMANDS_VARO_AUTOSTART_DELAY_HELP.send(sender);
				return;
			}

			int delay = -1;
			try {
				delay = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				Messages.COMMANDS_ERROR_NO_NUMBER.send(sender, Placeholder.constant("text", args[1]));
				return;
			}

			if (delay < 1) {
				Messages.COMMANDS_VARO_AUTOSTART_DELAY_TO_SMALL.send(sender);
				return;
			}

			Main.getVaroGame().getAutoStart().delay(delay);
            Messages.COMMANDS_VARO_AUTOSTART_START_DELAYED.send(sender, Placeholder.constant("autostart-delay", String.valueOf(delay)));
		} else if (args[0].equalsIgnoreCase("info")) {
			if (Main.getVaroGame().getAutoStart() == null)
			    Messages.COMMANDS_VARO_AUTOSTART_INACTIVE.send(sender);
			else
			    Messages.COMMANDS_VARO_AUTOSTART_INFO.send(sender, Placeholder.constant("autostart-date", new SimpleDateFormat("dd.MM.yyyy HH.mm").format(Main.getVaroGame().getAutoStart().getStart().toString())));
		} else
		    Messages.COMMANDS_ERROR_USAGE.send(sender, Placeholder.constant("command", "autostart"));
		return;

	}
}