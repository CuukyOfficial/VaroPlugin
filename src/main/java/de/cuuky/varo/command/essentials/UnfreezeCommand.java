package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.cuuky.varo.player.VaroPlayer;

public class UnfreezeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.unfreeze")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/freeze <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unfreeze <Player/@a>");
			return false;
		}

		if (args[0].equalsIgnoreCase("@a")) {
			for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				VaroCancelable.removeCancelable(player, CancelableType.FREEZE);
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler entfreezed!");
			return false;
		}

		if (Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer target = VaroPlayer.getPlayer(player);
		VaroCancelable.removeCancelable(target, CancelableType.FREEZE);
		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich entfreezed!");
		return false;
	}
}