package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class MuteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("varo.mute")) {
			sender.sendMessage(ConfigMessages.OTHER_NO_PERMISSION.getValue());
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/mute <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unmute <Player/@a>");
			return false;
		}

		if(args[0].equalsIgnoreCase("@a")) {
			for(VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				if(player.getPlayer().isOp()) {
					continue;
				}
				new VaroCancelAble(CancelAbleType.MUTE, player);
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler gemuted!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if(player.isOp()) {
			sender.sendMessage(Main.getPrefix() + "Ein Admin kann nicht gemutet werden!");
			return false;
		}

		VaroPlayer vp = VaroPlayer.getPlayer(player);
		new VaroCancelAble(CancelAbleType.MUTE, vp);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich gemuted!");
		return false;
	}
}