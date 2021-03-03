package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class CountdownCommand implements CommandExecutor {

	/*
	 * OLD CODE
	 */

	int sched = -1;
	int time = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VaroPlayer vp = (sender instanceof Player ? VaroPlayer.getPlayer((Player) sender) : null);
		if (!sender.hasPermission("varo.countdowm")) {
			sender.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(vp));
			return false;
		}

		if (sched != -1) {
			Bukkit.getScheduler().cancelTask(sched);
			sched = -1;

			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_ABORT.getValue(vp));
			return false;
		}

		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/countdown <seconds>");
			return false;
		}

		time = 0;
		try {
			time = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_NO_NUMBER.getValue(vp));
		}

		if (time < 1) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_TOO_SMALL.getValue(vp));
			return false;
		}

		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (time == 0) {
					Bukkit.broadcastMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_START.getValue(vp));
					Bukkit.getScheduler().cancelTask(sched);
					time = -1;
					sched = -1;
					return;
				}

				Bukkit.broadcastMessage(Main.getPrefix() + ConfigMessages.COMMANDS_COUNTDOWN_FORMAT.getValue(vp).replace("%seconds%", String.valueOf(time)));
				time--;
			}
		}, 0, 20);
		return false;
	}
}