package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.gui.player.PlayerGUI;
import de.cuuky.varo.utils.UUIDUtils;

public class PlayerCommand extends VaroCommand {

	public PlayerCommand() {
		super("player", "Verwaltet die Spieler", "varo.player");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- " + Main.getColorCode() + "Player §7-----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player §7<Spieler>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player add §7<Spieler1> <Spieler2> ...");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player remove §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player respawn §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player kill §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player reset §7<Spieler / @a>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player list");
			sender.sendMessage(Main.getPrefix() + "§7------------------");
			return;
		}

		if(args.length == 1 && VaroPlayer.getPlayer(args[0]) != null) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "§7Du musst Spieler sein, um diesen Command nutzen zu koennen!");
				return;
			}

			VaroPlayer vps = VaroPlayer.getPlayer(args[0]);
			if(vps == null) {
				sender.sendMessage(Main.getPrefix() + "Spieler nicht gefunden!");
				return;
			}

			new PlayerGUI((Player) sender, vps, null);
			return;
		}

		VaroPlayer vps = null;
		if(args.length > 1)
			vps = VaroPlayer.getPlayer(args[1]);

		if(args[0].equalsIgnoreCase("reset")) {
			if(args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for(VaroPlayer pl : VaroPlayer.getVaroPlayer()) {
					if(pl.isOnline())
						pl.getPlayer().kickPlayer("§cDein Account wurde resettet!\n§7Joine erneut, um dich zu registrieren.");
					pl.getStats().loadDefaults();
					if(pl.getTeam() != null)
						pl.getTeam().removeMember(pl);
				}
				return;
			}

			if(vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if(vps.isOnline())
				vps.getPlayer().kickPlayer("§7Dein Account wurde resettet!\nJoine erneut, um dich zu registrieren.");

			if(vps.isOnline())
				vps.getPlayer().kickPlayer("§cDein Account wurde resettet!\n§7Joine erneut, um dich zu registrieren.");
			vps.getStats().loadDefaults();
			if(vps.getTeam() != null)
				vps.getTeam().removeMember(vps);
			sender.sendMessage(Main.getPrefix() + "§7Account von §c" + vps.getName() + " §7wurde erfolgreich resettet!");
			return;
		} else if(args[0].equalsIgnoreCase("kill")) {
			if(args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for(VaroPlayer pl : VaroPlayer.getVaroPlayer())
					if(pl.isOnline())
						pl.getPlayer().setHealth(0);
					else
						pl.getStats().setState(PlayerState.DEAD);
				return;
			}

			if(vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if(vps.getStats().getState() == PlayerState.DEAD) {
				sender.sendMessage(Main.getPrefix() + "Dieser Spieler ist bereits tot!");
				return;
			}

			if(vps.isOnline())
				vps.getPlayer().setHealth(0);
			else
				vps.getStats().setState(PlayerState.DEAD);

			sender.sendMessage(Main.getPrefix() + "§7" + vps.getName() + " §7erfolgreich getoetet!");
			return;
		} else if(args[0].equalsIgnoreCase("remove")) {
			if(args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for(VaroPlayer pl : VaroPlayer.getVaroPlayer()) {
					if(pl.isOnline())
						pl.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue());

					pl.delete();
				}
				return;
			}

			if(vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if(vps.isOnline())
				vps.getPlayer().kickPlayer(ConfigMessages.JOIN_KICK_NOT_USER_OF_PROJECT.getValue());

			vps.delete();
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[1] + " §7wurde erfolgreich aus " + Main.getColorCode() + Main.getProjectName() + " §7entfernt!");
			return;
		} else if(args[0].equalsIgnoreCase("add")) {
			if(args.length <= 1) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player add §7<Spieler1> <Spieler2> ...");
				return;
			}

			for(String arg : args) {
				if(arg.equals(args[0]))
					continue;

				if(VaroPlayer.getPlayer(arg) != null) {
					sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7existiert bereits!");
					continue;
				}

				String uuid;
				try {
					uuid = UUIDUtils.getUUID(arg).toString();
				} catch(Exception e) {
					sender.sendMessage(Main.getPrefix() + "§c" + arg + " wurde nicht gefunden.");
					String newName;
					try {
						newName = UUIDUtils.getNamesChanged(arg);
						sender.sendMessage(Main.getPrefix() + "§cEin Spieler, der in den letzten 30 Tagen " + arg + " hiess, hat sich in §7" + newName + " §cumbenannt.");
						sender.sendMessage(Main.getPrefix() + "Benutze \"/varo team add\", um diese Person einem Team hinzuzufuegen.");
					} catch(Exception f) {
						sender.sendMessage(Main.getPrefix() + "§cIn den letzten 30 Tagen gab es keinen Spieler mit diesem Namen.");
					}
					continue;
				}

				new VaroPlayer(arg, uuid);
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + arg + " §7wurde erfolgreich zu " + Main.getColorCode() + Main.getProjectName() + " §7hinzugefuegt!");
			}
		} else if(args[0].equalsIgnoreCase("respawn")) {
			if(args.length >= 2 && args[1].equalsIgnoreCase("@a")) {
				for(VaroPlayer pl : VaroPlayer.getVaroPlayer())
					pl.getStats().setState(PlayerState.ALIVE);
				sender.sendMessage(Main.getPrefix() + "§7Erfolgreich alle Spieler wiederbelebt!");
				return;
			}

			if(vps == null) {
				sender.sendMessage(Main.getPrefix() + "§7Spieler nicht gefunden!");
				return;
			}

			if(vps.getStats().isAlive()) {
				sender.sendMessage(Main.getPrefix() + "§a" + vps.getName() + " §7lebt bereits!");
				return;
			}

			vps.getStats().setState(PlayerState.ALIVE);
			sender.sendMessage(Main.getPrefix() + "§a" + vps.getName() + " §7erfolgreich wiederbelebt!");
			return;
		} else if(args[0].equalsIgnoreCase("list")) {
			if(VaroPlayer.getVaroPlayer().isEmpty()) {
				sender.sendMessage(Main.getPrefix() + "Kein Spieler gefunden!");
				return;
			}

			int playerNumber = VaroPlayer.getVaroPlayer().size();
			int playerPages = 1 + (playerNumber / 50);

			int lastPlayerOnPage = 50;
			int page = 1;

			if(args.length != 1) {
				try {
					page = Integer.parseInt(args[1]);
				} catch(NumberFormatException e) {
					page = 1;
				}

				lastPlayerOnPage = page * 50;
			}

			if(page == playerPages) {
				lastPlayerOnPage = playerNumber;
			}

			if(page > playerPages) {
				sender.sendMessage(Main.getPrefix() + "Keine Seite " + page + " der Spieler gefunden!");
				return;
			}

			if(playerPages == 1) {
				sender.sendMessage(Main.getPrefix() + "§lListe aller " + Main.getColorCode() + " §lSpieler§7§l:");
			} else {
				sender.sendMessage(Main.getPrefix() + "§lListe der " + Main.getColorCode() + " §lSpieler§7§l " + ((page - 1) * 50 + 1) + " bis " + lastPlayerOnPage + ":");
			}

			for(int i = (page - 1) * 50; i < lastPlayerOnPage; i++) {
				VaroPlayer player = VaroPlayer.getVaroPlayer().get(i);
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§l" + (i + 1) + "§7: " + Main.getColorCode() + player.getName());
			}

			int lastPlayerNextSite = 0;
			if(page + 1 < playerPages)
				lastPlayerNextSite = (page + 1) * 50;
			else if(page + 1 == playerPages)
				lastPlayerNextSite = playerPages;

			if(page < playerPages)
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo player list " + (page + 1) + " §7fuer " + Main.getColorCode() + "Spieler §7 " + (page * 50 + 1) + " bis " + lastPlayerNextSite);
		} else
			sender.sendMessage(Main.getPrefix() + "§7Player/Command not found! §7Type /player for more.");
		return;
	}
}