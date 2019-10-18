package de.cuuky.varo.command.varo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.game.start.AutoStart;
import de.cuuky.varo.player.VaroPlayer;

public class AutoStartCommand extends VaroCommand {

	/*
	 * OLD CODE
	 */

	public AutoStartCommand() {
		super("autostart", "Startet das Varo automatisch", "varo.autostart", "as");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(Main.getGame().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7wurde bereits gestartet!");
			return;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7-------- " + Main.getColorCode() + "AutoStart §7-------");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7info");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7set <Hour> <Minute> <Day> <Month> <Year>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7remove");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo autostart §7delay <Minutes>");
			sender.sendMessage(Main.getPrefix() + "§7------------------------");
			return;
		}

		if(args[0].equalsIgnoreCase("set")) {
			if(Main.getGame().getAutoStart() != null) {
				sender.sendMessage(Main.getPrefix() + "§7Entferne erst den AutoStart, bevor du einen neuen setzt!");
				return;
			}

			if(args.length != 6) {
				sender.sendMessage(Main.getPrefix() + "§b/autostart §7set <Hour> <Minute> <Day> <Month> <Year>");
				return;
			}

			if(args[5].length() == 2)
				args[5] = 20 + args[5];

			int min, hour, day, month, year;
			try {
				min = Integer.parseInt(args[2]);
				hour = Integer.parseInt(args[1]);
				day = Integer.parseInt(args[3]);
				month = Integer.parseInt(args[4]) - 1;
				year = Integer.parseInt(args[5]);
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + "Eines der Argumente war §ckeine §7Zahl!");
				return;
			}

			Calendar start = new GregorianCalendar(year, month, day, hour, min, 0);
			if(new GregorianCalendar().after(start)) {
				sender.sendMessage(Main.getPrefix() + "§7Das " + Main.getColorCode() + "Datum §7darf nicht in der Vergangenheit sein!");
				return;
			}

			Main.getGame().setAutoStart(new AutoStart(start));
			return;
		} else if(args[0].equalsIgnoreCase("remove")) {
			if(Main.getGame().getAutoStart() == null) {
				sender.sendMessage(Main.getPrefix() + "§7Es wurde noch kein " + Main.getColorCode() + "Autostart §7festegelegt!");
				return;
			}

			Main.getGame().getAutoStart().stop();
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "AutoStart §7erfolgreich entfernt!");
		} else if(args[0].equalsIgnoreCase("delay")) {
			if(Main.getGame().getAutoStart() == null) {
				sender.sendMessage(Main.getPrefix() + "§7Es wurde noch kein " + Main.getColorCode() + "Autostart §7festegelegt!");
				return;
			}

			if(args.length < 2) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/autostart delay §7<Delay in Minutes>");
				return;
			}

			int delay = -1;
			try {
				delay = Integer.parseInt(args[1]);
			} catch(NumberFormatException e) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[1] + " §7ist keine Zahl!");
				return;
			}

			if(delay < 1) {
				sender.sendMessage(Main.getPrefix() + "Der Delay darf nicht kleiner als 1 sein!");
				return;
			}

			Main.getGame().getAutoStart().delay(delay);
			sender.sendMessage(Main.getPrefix() + "§7Der Start wurde um " + Main.getColorCode() + delay + " §7Minuten verzögert!");
		} else if(args[0].equalsIgnoreCase("info")) {
			if(Main.getGame().getAutoStart() == null)
				sender.sendMessage(Main.getPrefix() + "AutoStart nicht aktiv");
			else {
				sender.sendMessage(Main.getPrefix() + "AutoStart §aaktiv§7:");
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Datum: §7" + new SimpleDateFormat("dd.MM.yyyy HH.mm").format(Main.getGame().getAutoStart().getStart()));
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "AutoSort: §7" + ConfigEntry.DO_SORT_AT_START.getValueAsBoolean());
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "AutoRandomteam: §7" + ConfigEntry.DO_RANDOMTEAM_AT_START.getValueAsBoolean());
			}
		} else
			sender.sendMessage(Main.getPrefix() + "Not found! Type " + Main.getColorCode() + "/autostart §7for help!");
		return;

	}
}
