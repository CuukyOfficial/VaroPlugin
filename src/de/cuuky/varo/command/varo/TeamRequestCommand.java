package de.cuuky.varo.command.varo;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.request.VaroTeamRequest;
import de.cuuky.varo.gui.settings.VaroColorMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamRequestCommand extends VaroCommand {
	private static final String[] subCommands = {"help", "color", "invite", "accept", "decline", "revoke", "leave"};
	public TeamRequestCommand() {
		super("teamrequest", "Sendet einem anderen Spieler eine Teamanfrage", null, subCommands, "tr", "request");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer player, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			System.out.println("Not for console!");
			return;
		}

		if (args.length == 0) {
			sendInfo((Player) sender);
			return;
		} else if (args[0].equals("help")) {
			sendInfo((Player) sender);
			return;
		} else if (args[0].equals("color")) {
			if (player.getTeam() == null) {
				player.sendMessage(Main.getPrefix() + "Du bist in keinem Team!");
				return;
			}

			if (!player.getPlayer().hasPermission("varo.changeTeamColor")) {
				player.sendMessage(ConfigMessages.NOPERMISSION_NO_PERMISSION.getValue(player));
				return;
			}

			if (args.length >= 2 && args[1].equalsIgnoreCase("remove")) {
				player.getTeam().setColorCode(null);
				player.sendMessage(Main.getPrefix() + "Farbe erfolgreich entfernt!");
				return;
			}

			new VaroColorMenu(Main.getCuukyFrameWork().getAdvancedInventoryManager(), (Player) sender, varoMenuColor -> {
				player.getTeam().setColorCode(varoMenuColor.getColorCode());
				sender.sendMessage(Main.getPrefix() + "Team-Farbcode vom Team " + player.getTeam().getDisplay() + " §7erfolgreich geaendert!");
			}, true);
			return;
		}

		if (!ConfigSetting.TEAMREQUEST_ENABLED.getValueAsBoolean()) {
			sender.sendMessage(Main.getPrefix() + "§7Das " + Main.getColorCode() + "Teamanfragen-System §7wurde in der Config deaktiviert!");
			return;
		}

		if (Main.getVaroGame().hasStarted()) {
			sender.sendMessage(Main.getPrefix() + "§7Du kannst dein Team nicht wechseln, da " + Main.getProjectName() + " schon gestartet ist!");
			return;
		}

		if (args[0].equalsIgnoreCase("invite")) {
			if (args.length < 2) {
				sender.sendMessage(Main.getPrefix() + "§7/tr invite §7<Player 1, Player 2, ...>");
				return;
			}

			for (String arg : args) {
				if (arg.equals(args[0]))
					continue;

				VaroPlayer invite = VaroPlayer.getPlayer(arg);
				if (invite == null) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7nicht gefunden!");
					continue;
				}

				if (invite.equals(player)) {
					sender.sendMessage(Main.getPrefix() + "§7Du kannst dich nicht selbst einladen!");
					continue;
				}

				if (VaroTeamRequest.getByAll(player, invite) != null) {
					sender.sendMessage(Main.getPrefix() + "§7Du hast bereits eine Anfrage an §7" + arg + " §7verschickt! Versuche es in " + ConfigSetting.TEAMREQUEST_EXPIRETIME.getValueAsInt() + " Sekunden erneut!");
					return;
				}

				if (invite.getTeam() != null && player.getTeam() != null)
					if (invite.getTeam().equals(player.getTeam())) {
						sender.sendMessage(Main.getPrefix() + "§7Du und " + Main.getColorCode() + invite.getName() + " §7sind bereits im selben Team!");
						continue;
					}

				invite.sendMessage(ConfigMessages.TEAMREQUEST_TEAM_REQUEST_RECIEVED, invite).replace("%invitor%", player.getName());
				player.sendMessage(Main.getPrefix() + "Du hast eine Teamanfrage an " + Main.getColorCode() + invite.getName() + " §7gesendet");
				new VaroTeamRequest(player, invite);
			}
		} else if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("decline")) {
			if (args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "§7/tr accept/decline <Player>");
				return;
			}

			if (Bukkit.getPlayerExact(args[1]) == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler " + Main.getColorCode() + args[1] + " §7nicht gefunden!");
				return;
			}

			VaroPlayer varoPlayer = VaroPlayer.getPlayer(Bukkit.getPlayerExact(args[1]));
			VaroTeamRequest tr = VaroTeamRequest.getByAll(varoPlayer, player);

			if (tr == null) {
				sender.sendMessage(Main.getPrefix() + "§7Einladung von " + Main.getColorCode() + "" + args[1] + " §7nicht gefunden!");
				return;
			}

			if (args[0].equalsIgnoreCase("accept")) {
				player.sendMessage(Main.getPrefix() + "Du hast die Anfrage von " + Main.getColorCode() + varoPlayer.getName() + " §7angenommen");
				varoPlayer.sendMessage(Main.getPrefix() + Main.getColorCode() + player.getName() + " §7hat deine Team anfrage angenommen!");
				tr.accept();
			} else {
				player.sendMessage(Main.getPrefix() + "Du hast die Anfrage von " + Main.getColorCode() + varoPlayer.getName() + " §abgelehnt!");
				varoPlayer.sendMessage(Main.getPrefix() + Main.getColorCode() + player.getName() + " §7hat deine Team anfrage abgelehnt!");
				tr.decline();
			}
		} else if (args[0].equalsIgnoreCase("revoke")) {
			if (args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "§7/tr revoke <Player>");
				return;
			}

			if (Bukkit.getPlayer(args[1]) == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler §7" + args[1] + " §7nicht gefunden!");
				return;
			}

			VaroTeamRequest tr = VaroTeamRequest.getByInvitor(VaroPlayer.getPlayer(args[1]));

			if (tr == null) {
				sender.sendMessage(Main.getPrefix() + "§7Du hast keine Einladung an " + Main.getColorCode() + "" + args[1] + " §7verschickt!");
				return;
			}

			tr.revoke();
		} else if (args[0].equalsIgnoreCase("leave")) {
			if (args.length != 1) {
				sender.sendMessage(Main.getPrefix() + "§7/tr leave");
				return;
			}

			if (player.getTeam() == null) {
				sender.sendMessage(Main.getPrefix() + "§7Du bist in keinem Team!");
				return;
			}

			player.sendMessage(Main.getPrefix() + "§7Du hast dein Team " + Main.getColorCode() + player.getTeam().getDisplay() + " §7erfolgreich verlassen!");
			player.getTeam().removeMember(player);
		} else {
			player.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §kein Argument!");
			sendInfo(player.getPlayer());
		}

		return;
	}

	public void sendInfo(Player sender) {
		sender.sendMessage(Main.getPrefix() + "§7--- " + Main.getColorCode() + "TeamRequestor-System §7---");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr color §7[remove]");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr invite §7<Player 1, Player 2, ...>");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr revoke §7<Player>");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr decline §7<Player>");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr accept §7<Player>");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr leave");
		sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " tr help");
		sender.sendMessage(Main.getPrefix() + "§7--------------------------");
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 2 && subCommands != null) {
			List<String> subCommands = Arrays.asList(this.subCommands);
			list.addAll(subCommands);
		}
		ArrayList<String> completerList = new ArrayList<>();
		String curentarg = args[args.length - 1].toLowerCase();
		for (String s : list) {
			String s1 = s.toLowerCase();
			if (s1.startsWith(curentarg)) {
				completerList.add(s);
			}
		}
		return completerList;
	}
}