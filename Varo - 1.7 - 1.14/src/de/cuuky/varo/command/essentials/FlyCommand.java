package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;

public class FlyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.fly")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.fly"));
			return false;
		}

		if(args.length == 0) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Entweder /fly [Player] oder Spieler sein!");
				return false;
			}

			Player p = (Player) sender;
			boolean set = !p.getAllowFlight();
			p.setAllowFlight(set);
			p.setFlying(set);
			sender.sendMessage(Main.getPrefix() + "§7Du kannst jetzt " + (p.getAllowFlight() ? "§afliegen§7!" : "§7nicht mehr fliegen§7!"));
		} else if(args.length == 1) {
			Player to = Bukkit.getPlayerExact(args[0]);
			if(to == null) {
				sender.sendMessage(Main.getPrefix() + "§7" + args[0] + "§7 nicht gefunden!");
				return false;
			}

			boolean set = !to.getAllowFlight();
			to.setAllowFlight(set);
			to.setFlying(set);
			sender.sendMessage(Main.getPrefix() + ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString() + to.getName() + " §7kann jetzt " + (to.getAllowFlight() ? "§afliegen§7!" : "§7nicht mehr fliegen§7!"));
		} else
			sender.sendMessage(Main.getPrefix() + "§7/fly [Player]");
		return false;
	}
}
