package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class UnprotectCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.unprotect")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.unprotect"));
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/mute <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unmute <Player/@a>");
			return false;
		}

		if(args[0].equalsIgnoreCase("@a")) {
			for(VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				if(VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION) != null)
					VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION).remove();
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler unprotected!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer vp = VaroPlayer.getPlayer(player);
		if(VaroCancelAble.getCancelAble(vp, CancelAbleType.PROTECTION) != null)
			VaroCancelAble.getCancelAble(vp, CancelAbleType.PROTECTION).remove();

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich unprotected!");
		return false;
	}
}
