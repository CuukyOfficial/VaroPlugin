package de.cuuky.varo.command.varo;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.world.generators.LobbyGenerator;

public class LobbyCommand extends VaroCommand {

	private ArrayList<UUID> uuid;

	public LobbyCommand() {
		super("lobby", "Einstellungen zur Lobby", "varo.lobby");

		uuid = new ArrayList<>();
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Main.getPrefix() + "§7Only for Players");
			return;
		}

		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7------ " + Main.getColorCode() + "Lobby §7------");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby build §7<size> <height>");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby setSpawn");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby removeSpawn");
			sender.sendMessage(Main.getPrefix() + "§7-----------------------");
			return;
		}

		if(args[0].equalsIgnoreCase("build") || args[0].equalsIgnoreCase("create")) {
			if(args.length != 3) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/lobby build §7<size> <height>");
				return;
			}

			Player p = (Player) sender;
			if(!uuid.contains(p.getUniqueId())) {
				sender.sendMessage(Main.getPrefix() + "§7Dieser Command wird eine " + Main.getColorCode() + "Lobby §7um dich herum spawnen, was du nicht rückgängig machen kannst.");
				sender.sendMessage(Main.getPrefix() + "Führe diesen Command am besten " + Main.getColorCode() + "Hoch in der Luft §7aus.");
				sender.sendMessage(Main.getPrefix() + "Gib den Command nochmal ein, um zu " + Main.getColorCode() + "bestätigen§7.");
				uuid.add(p.getUniqueId());
				return;
			}

			int height = 12;
			int size = 25;
			try {
				size = Integer.valueOf(args[1]);
				height = Integer.valueOf(args[2]);
			} catch(Exception e) {
				sender.sendMessage(Main.getPrefix() + "§7Die Höhe und die Größe müssen eine Zahl sein!");
				return;
			}
			
			uuid.remove(p.getUniqueId());
			Main.getGame().setLobby(p.getLocation());
			new LobbyGenerator(p.getLocation(), height, size);
		} else if(args[0].equalsIgnoreCase("setSpawn") || args[0].equalsIgnoreCase("set")) {
			Main.getGame().setLobby(((Player) sender).getLocation());
			sender.sendMessage(Main.getPrefix() + "§7Location für die " + Main.getColorCode() + "Lobby §7erfolgreich gesetzt!");
			return;
		} else if(args[0].equalsIgnoreCase("removeSpawn") || args[0].equalsIgnoreCase("remove")) {
			Main.getGame().setLobby(null);
			sender.sendMessage(Main.getPrefix() + "§7Location für die " + Main.getColorCode() + "Lobby §7erfolgreich entfernt!");
			return;
		} else
			sender.sendMessage(Main.getPrefix() + "§7Not found. §7Type /lobby for help.");
	}
}
