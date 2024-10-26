package de.cuuky.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroChatListMessages;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.gui.player.PlayerGUI;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.varoplugin.cfw.chat.PageableChatBuilder;
import de.varoplugin.cfw.utils.PlayerProfileUtils.PlayerLookup;
import de.varoplugin.cfw.utils.PlayerProfileUtils.Result;

public class PlayerCommand extends VaroCommand {

	private PageableChatBuilder<VaroPlayer> listBuilder;

	public PlayerCommand() {
		super("player", "Verwaltet die Spieler", "varo.player", "players");

		this.listBuilder = new PageableChatBuilder<>(VaroPlayer::getVaroPlayers)
				.messages(new VaroChatListMessages<>(player ->
						Main.getPrefix() + Main.getColorCode() + "§l" + (player.getId() + 1) + "§7: " + Main.getColorCode() + player.getName(),
						"/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player list", "List aller Spieler"));
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Player §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player §7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player add §7<Spieler1> <Spieler2> ...");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player remove §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player respawn §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player kill §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player reset §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player list");
			sender.sendMessage(Main.getPrefix() + "§7------------------");
			return;
		}

		if (args.length == 1 && VaroPlayer.getPlayer(args[0]) != null) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Du musst Spieler sein, um diesen Command nutzen zu koennen!");
				return;
			}

			VaroPlayer vps = VaroPlayer.getPlayer(args[0]);
			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "Spieler nicht gefunden!");
				return;
			}

			new PlayerGUI((Player) sender, vps);
			return;
		}

		VaroPlayer vps = null;
		if (args.length > 1)
			vps = VaroPlayer.getPlayer(args[1]);

		if (args[0].equalsIgnoreCase("reset")) {
			if (args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayers()) {
					if (pl.isOnline())
						pl.getPlayer().kickPlayer("§cDein Account wurde resettet!\n§7Joine erneut, um dich zu registrieren.");
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
			sender.sendMessage(Main.getPrefix() + "§7Account von §c" + vps.getName() + " §7wurde erfolgreich resettet!");
			return;
		} else if (args[0].equalsIgnoreCase("kill")) {
			if (args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayers())
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

			sender.sendMessage(Main.getPrefix() + "§7" + vps.getName() + " §7erfolgreich getoetet!");
			return;
		} else if (args[0].equalsIgnoreCase("remove")) {
			if (args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : new ArrayList<>(VaroPlayer.getVaroPlayers())) {
					if (pl.isOnline())
						pl.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue(vp));

					pl.delete();
				}
				return;
			}

			if (vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if (vps.isOnline())
				vps.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue(vp));

			vps.delete();
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[1] + " §7wurde erfolgreich aus " + Main.getColorCode() + Main.getProjectName() + " §7entfernt!");
			return;
		} else if (args[0].equalsIgnoreCase("add")) {
			if (args.length <= 1) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " player add §7<Spieler1> <Spieler2> ...");
				return;
			}

			for (String arg : args) {
				if (arg.equals(args[0]))
					continue;

				if (VaroPlayer.getPlayer(arg) != null) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7existiert bereits!");
					continue;
				}

				PlayerLookup lookup = Main.lookupPlayer(arg);
				if (lookup.getResult() == Result.UNKNOWN_PLAYER) {
				    sender.sendMessage(Main.getPrefix() + "§c" + arg + " wurde nicht gefunden.");
                    continue;
				}
				if (lookup.getResult() != Result.SUCCESS) {
				    lookup.getException().printStackTrace();
				    sender.sendMessage(Main.getPrefix() + "§c" + arg + " wurde nicht gefunden, da ein Fehler aufgetreten ist.");
                    continue;
				}

				new VaroPlayer(arg, lookup.getUuid().toString());
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7wurde erfolgreich zu " + Main.getColorCode() + Main.getProjectName() + " §7hinzugefuegt!");
			}
		} else if (args[0].equalsIgnoreCase("respawn")) {
			if (args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for (VaroPlayer pl : VaroPlayer.getVaroPlayers())
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
		} else if (args[0].equalsIgnoreCase("list")) {
            this.listBuilder.page(args.length >= 2 ? args[1] : "1").build().send(sender);
		} else
			sender.sendMessage(Main.getPrefix() + "§7Player/Command not found! §7Type /player for more.");
		return;
	}
}