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

public class MuteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.freeze")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.mute"));
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/mute <Player>");
			return false;
		}

		if(args[0].equalsIgnoreCase("all")) {
			for(Player player : Bukkit.getOnlinePlayers())
				if(VaroCancelAble.getCancelAble(player, CancelAbleType.MUTE) != null)
					VaroCancelAble.getCancelAble(player, CancelAbleType.MUTE).remove();
				else
					new VaroCancelAble(CancelAbleType.MUTE, player);

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler (ent- / ge-) muted!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if(VaroCancelAble.getCancelAble(player, CancelAbleType.MUTE) != null)
			VaroCancelAble.getCancelAble(player, CancelAbleType.MUTE).remove();
		else
			new VaroCancelAble(CancelAbleType.MUTE, player);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich " + (VaroCancelAble.getCancelAble(player, CancelAbleType.MUTE) != null ? "gemuted!" : "entmuted!"));
		return false;
	}
}
