package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class UnmuteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if(!sender.hasPermission("varo.unmute")) {
			sender.sendMessage(Main.getLanguageManager().getValue(LanguageDE.NOPERMISSION_NO_PERMISSION, vp));
			return false;
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/mute <Player/@a>");
			sender.sendMessage(Main.getPrefix() + "§7/unmute <Player/@a>");
			return false;
		}

		if(args[0].equalsIgnoreCase("@a")) {
			for(VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
				VaroCancelAble.removeCancelAble(player, CancelAbleType.MUTE);
			}

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler entmuted!");
			return false;
		}

		if(Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		VaroPlayer target = VaroPlayer.getPlayer(player);
		VaroCancelAble.removeCancelAble(target, CancelAbleType.MUTE);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich entmuted!");
		return false;
	}
}