package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.threads.LagCounter;

public class PerformanceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.performance")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/performance clear ß8- ß7F√ºhrt einen RAM-Cleaner aus");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/performance help ß8- ß7Zeigt Methoden zur Performanceverbesserung");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/performance entityclear ß8- ß7Entfernt Items auf dem Boden, Tiere etc. (alles au√üer Spielern)");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "TIPP: ß7/usage zeigt die Ausnutzung deines Servers");
			return false;
		}

		if(args[0].equalsIgnoreCase("improve") || args[0].equalsIgnoreCase("clear")) {
			System.gc();
			sender.sendMessage(Main.getPrefix() + "RAM weitesgehend entleert!");
		} else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Main.getPrefix() + "Derzeitige TPS: ßc" + Math.round(LagCounter.getTPS()) + "ß7 - Normalwert ßc18-20 ß7TPS");
			
			sender.sendMessage(Main.getPrefix() + "Folgende Einstellungen k√∂nnten die Performance vermindern - das Ausschalten erhoeht eventuell die Performance:");
			sender.sendMessage(Main.getPrefix());
			for(ConfigEntry ce : ConfigEntry.values())
				if(ce.isReducingPerformance() && (ce.getValue() instanceof Boolean && ce.getValueAsBoolean()))
					sender.sendMessage(Main.getPrefix() + "- ß7Die Einstellung ßc" + ce.getName() + " ß7vermindert die Performance");

			int entities = 0;
			for(World world : Bukkit.getWorlds())
				entities += world.getEntities().size();

			sender.sendMessage(Main.getPrefix() + "Es sind ßc" + entities + " ß7Entities (inklusive Spieler, ArmorStands, Tiere etc.) geladen - alle nicht-Spieler zu entfernen k√∂nnte die Performance erh√∂hen");
			sender.sendMessage(Main.getPrefix() + "Es sind ßc" + Bukkit.getPluginManager().getPlugins().length + " ß7Plugins aktiviert - bitte alle nicht n√∂tigen entfernen");
		} else if(args[0].equalsIgnoreCase("entityclear")) {
			for(World world : Bukkit.getWorlds())
				for(Entity entity : world.getEntities())
					if(!(entity instanceof Player))
						entity.remove();

			sender.sendMessage(Main.getPrefix() + "Alle Nicht-Spieler entfernt!");
		}

		return false;
	}
}