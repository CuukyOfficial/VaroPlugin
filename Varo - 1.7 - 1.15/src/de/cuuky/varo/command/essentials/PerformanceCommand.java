package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.threads.LagCounter;

public class PerformanceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if(!sender.hasPermission("varo.performance")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§c§lPerformance Command§7§l:");
			sender.sendMessage(Main.getPrefix() + "§c/performance clear §8- §7Fuehrt einen RAM-Cleaner aus");
			sender.sendMessage(Main.getPrefix() + "§c/performance help §8- §7Zeigt Methoden zur Performanceverbesserung");
			sender.sendMessage(Main.getPrefix() + "§c/performance entityclear §8- §7Entfernt Items auf dem Boden etc. (ausgenommen Spieler, ArmorStands, Tiere)");
			sender.sendMessage(Main.getPrefix() + "§cTIPP: §7/usage zeigt die Ausnutzung deines Servers");
			return false;
		}

		if(args[0].equalsIgnoreCase("improve") || args[0].equalsIgnoreCase("clear")) {
			Runtime r = Runtime.getRuntime();
			double ramUsage = (r.totalMemory() - r.freeMemory()) / 1048576;
			System.gc();
			double ramCleared = ramUsage - (r.totalMemory() - r.freeMemory()) / 1048576;
			sender.sendMessage(Main.getPrefix() + "RAM wurde um §c" + ramCleared + "MB §7geleert!");
		} else if(args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(Main.getPrefix() + "Derzeitige TPS: §c" + Math.round(LagCounter.getTPS()) + "§7 - Normalwert §c18-20 §7TPS");

			sender.sendMessage(Main.getPrefix() + "Folgende Einstellungen koennten die Performance vermindern - das Ausschalten erhoeht eventuell die Performance:");
			sender.sendMessage(Main.getPrefix());
			for(ConfigSetting ce : ConfigSetting.values())
				if(ce.isReducingPerformance() && (ce.getValue() instanceof Boolean && ce.getValueAsBoolean()))
					sender.sendMessage(Main.getPrefix() + "- §7Die Einstellung §c" + ce.getPath() + " §7vermindert die Performance");

			int entities = 0;
			for(World world : Bukkit.getWorlds())
				for(Entity entity : world.getEntities())
					if(!(entity.getType().toString().contains("ARMOR_STAND")) && !(entity instanceof LivingEntity))
						entities++;

			sender.sendMessage(Main.getPrefix() + "Es sind §c" + entities + " §7Entities (ausgenommen Spieler, ArmorStands, Tiere) geladen - alle nicht-Spieler zu entfernen koennte die Performance erhoehen");
			sender.sendMessage(Main.getPrefix() + "Es sind §c" + Bukkit.getPluginManager().getPlugins().length + " §7Plugins aktiviert - bitte alle nicht noetigen entfernen");
		} else if(args[0].equalsIgnoreCase("entityclear")) {
			for(World world : Bukkit.getWorlds())
				for(Entity entity : world.getEntities())
					if(!(entity.getType().toString().contains("ARMOR_STAND")) && !(entity instanceof LivingEntity))
						entity.remove();

			sender.sendMessage(Main.getPrefix() + "Alle Nicht- Spieler,Tiere oder ArmorStands entfernt!");
		}

		return false;
	}
}