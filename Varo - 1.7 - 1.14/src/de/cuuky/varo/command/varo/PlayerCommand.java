package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.gui.player.PlayerGUI;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.utils.UUIDUtils;

public class PlayerCommand extends VaroCommand {

	public PlayerCommand() {
		super("player", "Verwaltet die Spieler", "varo.player");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 1 && VaroPlayer.getPlayer(args[0]) != null) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Du musst Spieler sein, um diesen Command nutzen zu können!");
				return;
			}

			VaroPlayer vps = VaroPlayer.getPlayer(args[0]);
			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "Spieler nicht gefunden!");
				return;
			}

			new PlayerGUI((Player) sender, vps, null);
			return;
		} else if (args.length == 1) {
			sender.sendMessage(Main.getPrefix() + "Spieler nicht gefunden!");
			return;
		}

		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Player §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player §7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player add §7<Player1> <Player2> ...");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player remove §7<Spieler / All>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player respawn §7<Player / All>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player kill §7<Spieler / All>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player reset §7<Spieler / All>");
			sender.sendMessage(Main.getPrefix() + "§7------------------");
			return;
		}

		VaroPlayer vps = VaroPlayer.getPlayer(args[1]);
		if (args[0].equalsIgnoreCase("reset")) {
			if (args[1].equalsIgnoreCase("all")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayer()) {
					if (pl.isOnline())
						pl.getPlayer()
								.kickPlayer("§cDein Account wurde resettet!\n§7Joine erneut, um dich zu registrieren.");
					pl.getStats().loadDefaults();
					if (pl.getTeam() != null)
						pl.getTeam().removeMember(pl);
				}
				return;
			}

			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if (vps.isOnline())
				vps.getPlayer().kickPlayer("§7Dein Account wurde resettet!\nJoine erneut, um dich zu registrieren.");

			if (vps.isOnline())
				vps.getPlayer().kickPlayer("§cDein Account wurde resettet!\n§7Joine erneut, um dich zu registrieren.");
			vps.getStats().loadDefaults();
			if (vps.getTeam() != null)
				vps.getTeam().removeMember(vps);
			sender.sendMessage(
					Main.getPrefix() + "§7Account von §c" + vps.getName() + " §7wurde erfolgreich resettet!");
			return;
		} else if (args[0].equalsIgnoreCase("kill")) {
			if (args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayer())
					if (pl.isOnline())
						pl.getPlayer().setHealth(0);
					else
						pl.getStats().setState(PlayerState.DEAD);
				return;
			}

			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if (vps.getStats().getState() == PlayerState.DEAD) {
				sender.sendMessage(Main.getPrefix() + "Dieser Spieler ist bereits tot!");
				return;
			}

			if (vps.isOnline())
				vps.getPlayer().setHealth(0);
			else
				vps.getStats().setState(PlayerState.DEAD);

			sender.sendMessage(Main.getPrefix() + "§7" + vps.getName() + " §7erfolgreich getötet!");
			return;
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayer()) {
					if (pl.isOnline())
						pl.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue());

					pl.delete();
				}
				return;
			}

			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if (vps.isOnline())
				vps.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue());

			vps.delete();
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "" + args[1] + " §7wurde erfolgreich aus "
					+ Main.getColorCode() + Main.getProjectName() + " §7entfernt!");
			return;
		} else if (args[0].equalsIgnoreCase("add")) {
			for (String arg : args) {
				if (arg.equals(args[0]))
					continue;

				if (VaroPlayer.getPlayer(arg) != null) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7existiert bereits!");
					continue;
				}

				String uuid = null;
				try {
					uuid = UUIDUtils.getUUID(arg).toString();
				} catch (Exception e) {
					sender.sendMessage(Main.getPrefix() + arg + " besitzt keinen Minecraft-Account!");
					continue;
				}

				new VaroPlayer(arg, uuid);
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7wurde erfolgreich zu "
						+ Main.getColorCode() + Main.getProjectName() + " §7hinzugefügt!");
			}
		} else if (args[0].equalsIgnoreCase("respawn")) {
			if (args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayer())
					pl.getStats().setState(PlayerState.ALIVE);
				sender.sendMessage(Main.getPrefix() + "§7Erfolgreich alle Spieler wiederbelebt!");
				return;
			}

			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if (vps.getStats().isAlive()) {
				sender.sendMessage(Main.getPrefix() + "§a" + vps.getName() + " §7lebt bereits!");
				return;
			}

			vps.getStats().setState(PlayerState.ALIVE);
			sender.sendMessage(Main.getPrefix() + "§a" + vps.getName() + " §7erfolgreich wiederbelebt!");
			return;
		} else
			sender.sendMessage(Main.getPrefix() + "§7Not found! §7Type /player for more.");
		return;
	}

}
