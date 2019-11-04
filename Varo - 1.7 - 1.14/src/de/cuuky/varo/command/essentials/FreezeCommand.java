package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class FreezeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.freeze")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.freeze"));
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/freeze <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unfreeze <Player/@a>");
			return false;
		}

		if(args[0].equalsIgnoreCase("@a")) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				if(player.isOp()) {
					continue;
				}
				
				if(VaroCancelAble.getCancelAble(player, CancelAbleType.FREEZE) == null)
					new VaroCancelAble(CancelAbleType.FREEZE, player);
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler gefreezed!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if(player.isOp()) {
			sender.sendMessage(Main.getPrefix() + "Ein Admin kann nicht gefreezed werden!");
			return false;
		}
		
		if(VaroCancelAble.getCancelAble(player, CancelAbleType.FREEZE) == null)
			new VaroCancelAble(CancelAbleType.FREEZE, player);
			

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich gefreezed!");
		return false;
	}
}
