package de.cuuky.varo.command.essentials;

import de.cuuky.varo.config.messages.ConfigMessages;
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

public class ProtectCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.protect")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/protect <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unprotect <Player/@a>");
			return false;
		}

		if(args[0].equalsIgnoreCase("@a")) {
			for(VaroPlayer player : VaroPlayer.getOnlinePlayer())
				new VaroCancelAble(CancelAbleType.PROTECTION, player);

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler protected!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer vp = VaroPlayer.getPlayer(player);
		new VaroCancelAble(CancelAbleType.PROTECTION, vp);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich protected!");
		return false;
	}
}