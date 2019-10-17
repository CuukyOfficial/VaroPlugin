package de.cuuky.varo.command.essentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.config.ConfigEntry;

public class SpeedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("varo.speed")) {
			sender.sendMessage(VaroCommand.getNoPermission("varo.speed"));
			return false;
		}

		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Entweder '/speed <Speed> [Player]' oder Spieler sein!");
				return false;
			}

			Player p = (Player) sender;
			Float speed = null;
			try {
				speed = Float.valueOf(args[0]);
				speed = getRealMoveSpeed(Float.valueOf(args[0]), p.isFlying());
			} catch (Exception e) {
				sender.sendMessage(Main.getPrefix() + "§7Du hast gültigen keinen §bSpeed §7angegeben!");
				return false;
			}

			if (Float.valueOf(args[0]) > 10 || Float.valueOf(args[0]) < 0) {
				sender.sendMessage(Main.getPrefix() + "§7Der Speed muss 0-10 betragen!");
				return false;
			}

			if (p.isFlying())
				p.setFlySpeed(speed);
			else
				p.setWalkSpeed(speed);
			sender.sendMessage(Main.getPrefix() + "§7Deine " + ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString()
					+ (p.isFlying() ? "Flug" : "Lauf") + "-Geschwindigkeit §7beträgt nun " + args[0] + "!");
		} else if (args.length == 2) {
			try {
				if (Float.valueOf(args[0]) > 10 || Float.valueOf(args[0]) < 0) {
					sender.sendMessage(Main.getPrefix() + "§7Der Speed muss 0-10 betragen!");
					return false;
				}
			} catch (Exception e) {
				sender.sendMessage(
						Main.getPrefix() + "§7Du hast gültigen keinen " + Main.getColorCode() + "Speed §7angegeben!");
				return false;
			}

			if (args[1].equalsIgnoreCase("all")) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					Float speed = null;
					try {
						speed = Float.valueOf(args[0]);
						speed = getRealMoveSpeed(Float.valueOf(args[0]), pl.isFlying());
					} catch (Exception e) {
						sender.sendMessage(Main.getPrefix() + "§7Du hast gültigen keinen " + Main.getColorCode()
								+ "Speed §7angegeben!");
						return false;
					}

					if (pl.isFlying())
						pl.setFlySpeed(speed);
					else
						pl.setWalkSpeed(speed);
				}
				sender.sendMessage(Main.getPrefix() + "§7Speed erfolgreich für alle gesetzt!");
				return false;
			}

			Player to = Bukkit.getPlayerExact(args[1]);
			if (to == null) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[1] + "§7 nicht gefunden!");
				return false;
			}

			Float speed = null;
			try {
				speed = Float.valueOf(args[0]);
				speed = getRealMoveSpeed(Float.valueOf(args[0]), to.isFlying());
			} catch (Exception e) {
				sender.sendMessage(Main.getPrefix() + "§7Du hast gültigen keinen §bSpeed §7angegeben!");
				return false;
			}

			if (to.isFlying())
				to.setFlySpeed(speed);
			else
				to.setWalkSpeed(speed);
			sender.sendMessage(Main.getPrefix() + "§7" + to.getName() + "'s "
					+ ConfigEntry.PROJECTNAME_COLORCODE.getValueAsString() + (to.isFlying() ? "Flug" : "Lauf")
					+ "-Geschwindigkeit §7beträgt nun " + args[0] + "!");
		} else
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/speed §7<Speed> [Player]");
		return false;
	}

	private float getRealMoveSpeed(final float userSpeed, final boolean isFly) {
		float defaultSpeed = isFly ? 0.1f : 0.2f;
		float maxSpeed = 1f;

		if (userSpeed < 1f) {
			return defaultSpeed * userSpeed;
		} else {
			float ratio = ((userSpeed - 1) / 9) * (maxSpeed - defaultSpeed);
			return ratio + defaultSpeed;
		}
	}
}
