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

public class ProtectCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.protect")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.protect"));
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/protect <Player>");
			return false;
		}

		if (args[0].equalsIgnoreCase("all")) {
			for (Player player : Bukkit.getOnlinePlayers())
				if (VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION) != null)
					VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION).remove();
				else
					new VaroCancelAble(CancelAbleType.PROTECTION, player);

			sender.sendMessage(Main.getPrefix() + "Erfolgreich alle Spieler (ent- / ge-) protected!");
			return false;
		}

		if (Bukkit.getPlayerExact(args[0]) == null) {
			sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7nicht gefunden!");
			return false;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if (VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION) != null)
			VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION).remove();
		else
			new VaroCancelAble(CancelAbleType.PROTECTION, player);

		sender.sendMessage(Main.getPrefix() + "§7" + args[0] + " §7erfolgreich "
				+ (VaroCancelAble.getCancelAble(player, CancelAbleType.PROTECTION) != null ? "protected!"
						: "entprotected!"));
		return false;
	}
}
