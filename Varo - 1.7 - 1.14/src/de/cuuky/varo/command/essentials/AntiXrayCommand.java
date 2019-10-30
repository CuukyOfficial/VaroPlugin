package de.cuuky.varo.command.essentials;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;

public class AntiXrayCommand implements CommandExecutor {
	
	private boolean antiXrayActivated;
	private String Version;
	private byte xrayAvailable;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.xray")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.xray"));
			return false;
		}
		Version = Bukkit.getBukkitVersion();
		
		if (Version.contains("1.8") || Version.contains("1.7")) {
			xrayAvailable = 0;
		} else if (Version.contains("1.9") || Version.contains("1.10") || Version.contains("1.11") || Version.contains("1.12") || Version.contains("1.13")) {
			xrayAvailable = 1;
		} else {
			xrayAvailable = 2;
		}
		
		if (Bukkit.getServer().spigot() == null) {
			if (xrayAvailable == 1) {
				xrayAvailable = 3;
			} else {
				xrayAvailable = 4;
			}
		} else {
			String enabled = Bukkit.getServer().spigot().getConfig().getString("world-settings.default.anti-xray.enabled");
			String engineMode = Bukkit.getServer().spigot().getConfig().getString("world-settings.default.anti-xray.engine-mode");
			if (!enabled.contentEquals("true") || !engineMode.contentEquals("2")) {
				antiXrayActivated = false;
			} else {
				antiXrayActivated = true;
			}
		}
		
				
		if(args.length != 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) {
			sender.sendMessage(Main.getPrefix() + "§7Anti-Xray momentan §l" + (antiXrayActivated ? "aktiviert" : "deaktiviert"));
			sender.sendMessage(Main.getPrefix() + "§7/antixray on - Aktiviert den Schutz vor X-Ray");
			sender.sendMessage(Main.getPrefix() + "§7/antixray off - Deaktiviert den Schutz vor X-Ray");
			return false;
		}
		
		if (xrayAvailable == 1) {
			sender.sendMessage(Main.getPrefix() + "In deiner Serverversion ist kein eingebautes Anti-Xray verfügbar.");
			sender.sendMessage(Main.getPrefix() + "Du kannst du dir ein externes hier herunterladen: https://www.spigotmc.org/resources/22818/");
			return false;
		} else if (xrayAvailable == 2 || xrayAvailable == 4) {
			sender.sendMessage(Main.getPrefix() + "In deiner Serverversion ist kein Anti-Xray verfügbar.");
			return false;
		} else if (xrayAvailable == 3) {
			sender.sendMessage(Main.getPrefix() + "In deiner Serverversion ist kein eingebautes Anti-Xray verfügbar.");
			sender.sendMessage(Main.getPrefix() + "Du kannst du dir ein externes hier herunterladen: https://dev.bukkit.org/projects/orebfuscator");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("on")) {
			if (antiXrayActivated) {
				sender.sendMessage(Main.getPrefix() + "§7Das Anti-Xray ist bereits aktiviert.");
				return false;
			} else {
				Bukkit.getServer().spigot().getConfig().set("world-settings.default.anti-xray.enabled", true);
				Bukkit.getServer().spigot().getConfig().set("world-settings.default.anti-xray.engine-mode", 2);
				
				try {
					Bukkit.getServer().spigot().getConfig().save("spigot.yml");
				} catch (IOException e) {
					sender.sendMessage(Main.getPrefix() + "§cFehler: §7Das Anti-Xray konnte nicht aktiviert werden.");
					return false;
				}
				
				sender.sendMessage(Main.getPrefix() + "§7Das Anti-Xray wurde aktiviert.");
			}
		} else if (args[0].equalsIgnoreCase("off")) {
			if (!antiXrayActivated) {
				sender.sendMessage(Main.getPrefix() + "§7Das Anti-Xray ist bereits deaktiviert.");
				return false;
			} else {
				Bukkit.getServer().spigot().getConfig().set("world-settings.default.anti-xray.enabled", true);
				Bukkit.getServer().spigot().getConfig().set("world-settings.default.anti-xray.engine-mode", 1);
				
				try {
					Bukkit.getServer().spigot().getConfig().save("spigot.yml");
				} catch (IOException e) {
					sender.sendMessage(Main.getPrefix() + "§cFehler: §7Das Anti-Xray konnte nicht deaktiviert werden.");
					return false;
				}
				
				sender.sendMessage(Main.getPrefix() + "§7Das Anti-Xray wurde deaktiviert.");
			}
		}
		
		Bukkit.getServer().shutdown();
		return false;
		
	}
}
