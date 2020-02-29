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
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/performance clear §8- §7Führt einen RAM-Cleaner aus");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/performance help §8- §7Zeigt Methoden zur Performanceverbesserung");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/performance entityclear §8- §7Entfernt Items auf dem Boden, Tiere etc. (alles außer Spielern)");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "TIPP: §7/usage zeigt die Ausnutzung deines Servers");
			return false;
		}

		if(args[0].equalsIgnoreCase("improve") || args[0].equalsIgnoreCase("clear")) {
			System.gc();
			sender.sendMessage(Main.getPrefix() + "RAM weitesgehend entleert!");
		} else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Main.getPrefix() + "Derzeitige TPS: §c" + Math.round(LagCounter.getTPS()) + "§7 - Normalwert §c18-20 §7TPS");
			
			sender.sendMessage(Main.getPrefix() + "Folgende Einstellungen könnten die Performance vermindern - das Ausschalten erhoeht eventuell die Performance:");
			sender.sendMessage(Main.getPrefix());
			for(ConfigEntry ce : ConfigEntry.values())
				if(ce.isReducingPerformance() && (ce.getValue() instanceof Boolean && ce.getValueAsBoolean()))
					sender.sendMessage(Main.getPrefix() + "- §7Die Einstellung §c" + ce.getName() + " §7vermindert die Performance");

			int entities = 0;
			for(World world : Bukkit.getWorlds())
				entities += world.getEntities().size();

			sender.sendMessage(Main.getPrefix() + "Es sind §c" + entities + " §7Entities (inklusive Spieler, ArmorStands, Tiere etc.) geladen - alle nicht-Spieler zu entfernen könnte die Performance erhöhen");
			sender.sendMessage(Main.getPrefix() + "Es sind §c" + Bukkit.getPluginManager().getPlugins().length + " §7Plugins aktiviert - bitte alle nicht nötigen entfernen");
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