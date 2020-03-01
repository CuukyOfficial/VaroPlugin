package de.cuuky.varo.command.varo;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.generators.LobbyGenerator;

public class LobbyCommand extends VaroCommand {

	private ArrayList<UUID> uuid;

	public LobbyCommand() {
		super("lobby", "Einstellungen zur Lobby", "varo.lobby");

		uuid = new ArrayList<>();
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.getPrefix() + "ß7Only for Players");
			return;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "ß7------ " + Main.getColorCode() + "Lobby ß7------");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby build ß7<size> <height>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby setSpawn");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby removeSpawn");
			sender.sendMessage(Main.getPrefix() + "ß7-----------------------");
			return;
		}

		if(args[0].equalsIgnoreCase("build") || args[0].equalsIgnoreCase("create")) {
			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby build ß7<size> <height>");
				return;
			}

			Player p = (Player) sender;
			if(!uuid.contains(p.getUniqueId())) {
				sender.sendMessage(Main.getPrefix() + "ß7Dieser Command wird eine " + Main.getColorCode() + "Lobby ß7um dich herum spawnen, was du nicht r√ºckg√§ngig machen kannst.");
				sender.sendMessage(Main.getPrefix() + "F√ºhre diesen Command am besten " + Main.getColorCode() + "Hoch in der Luft ß7aus.");
				sender.sendMessage(Main.getPrefix() + "Gib den Command nochmal ein, um zu " + Main.getColorCode() + "best√§tigenß7.");
				uuid.add(p.getUniqueId());
				return;
			}

			int height = 12;
			int size = 25;
			try {
				size = Integer.valueOf(args[1]);
				height = Integer.valueOf(args[2]);
			} catch(Exception e) {
				sender.sendMessage(Main.getPrefix() + "ß7Die H√∂he und die Gr√∂√üe m√ºssen eine Zahl sein!");
				return;
			}

			uuid.remove(p.getUniqueId());
			Main.getVaroGame().setLobby(p.getLocation());
			new LobbyGenerator(p.getLocation(), height, size);
		} else if(args[0].equalsIgnoreCase("setSpawn") || args[0].equalsIgnoreCase("set")) {
			Main.getVaroGame().setLobby(((Player) sender).getLocation());
			sender.sendMessage(Main.getPrefix() + "ß7Location f√ºr die " + Main.getColorCode() + "Lobby ß7erfolgreich gesetzt!");
			return;
		} else if(args[0].equalsIgnoreCase("removeSpawn") || args[0].equalsIgnoreCase("remove")) {
			Main.getVaroGame().setLobby(null);
			sender.sendMessage(Main.getPrefix() + "ß7Location f√ºr die " + Main.getColorCode() + "Lobby ß7erfolgreich entfernt!");
			return;
		} else
			sender.sendMessage(Main.getPrefix() + "ß7Not found. ß7Type /lobby for help.");
	}
}