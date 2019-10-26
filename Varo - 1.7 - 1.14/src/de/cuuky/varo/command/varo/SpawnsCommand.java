package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.spawns.spawn.SpawnGenerator;
import de.cuuky.varo.spawns.spawn.SpawnType;

public class SpawnsCommand extends VaroCommand {

	public SpawnsCommand() {
		super("spawns", "Hauptbefehl für die Spawns, in welchen die Spieler spawnen", "varo.spawns", "spawnholes", "holes");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onCommand(CommandSender sender, final VaroPlayer vp, final Command cmd, final String label, final String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lSpawn Commands§7§l:");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 set [Zahl/Spieler]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 delete [Zahl/Spieler] - (Löscht den Spawneintrag und den Spawn in der Welt)");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 list");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 generate <radius> <amount> [Half-Step-Materiall] [Side-Block-Material]");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Example: §7/varo spawns generate 30 40 STONE_SLAB");
			return;
		}

		if(args[0].equalsIgnoreCase("generate")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
				return;
			}

			if(args.length < 3) {
				sender.sendMessage(Main.getPrefix() + "/varo spawns§7 generate <radius> <amount> [Half-Step-Materiall] [Side-Block-Material]");
				return;
			}

			String material = null;
			if(args.length == 4)
				material = String.valueOf(args[3]);

			String sideBlockMaterial = null;
			if(args.length == 5)
				sideBlockMaterial = String.valueOf(args[4]);

			try {
				new SpawnGenerator(((Player) sender).getLocation(), Integer.valueOf(args[1]), Integer.valueOf(args[2]), material, sideBlockMaterial);
			} catch(Exception e) {
				sender.sendMessage(Main.getPrefix() + "Beim erstellen der Spawns ist ein Fehler aufgetreten!");
				e.printStackTrace();
				return;
			}

			sender.sendMessage(Main.getPrefix() + "§7Die Spawns wurden mit der Anzahl " + Main.getColorCode() + args[2] + "§7, dem Radius " + Main.getColorCode() + args[1] + "§7, dem Block-Material " + Main.getColorCode() + (args.length >= 4 ? args[3] : "STONE_BRICK_SLAB") + " §7und dem Seitenblock-Material " + Main.getColorCode() + (args.length >= 5 ? args[4] : "DIRT") + " §7generiert!");
			return;
		} else if(args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("place")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Not for console!");
				return;
			}

			Player player = (Player) sender;
			if(args.length == 1) {
				final Spawn spawn = new Spawn(SpawnType.NUMBERS, player.getLocation());
				player.sendMessage(Main.getPrefix() + Main.getColorCode() + "Spawn " + spawn.getNumber() + " §7erfolgreich erstellt!");
			} else if(args.length == 2) {
				int spawnNumber = -1;
				try {
					spawnNumber = Integer.valueOf(args[1]);
					if(!(spawnNumber > 0)) {
						player.sendMessage(Main.getPrefix() + "Spawn Zahl muss positiv sein!");
						return;
					}
				} catch(NumberFormatException e) {}

				if(spawnNumber != -1) {
					final Spawn oldSpawn = Spawn.getSpawn(spawnNumber);
					if(oldSpawn != null) {
						oldSpawn.delete();
						sender.sendMessage(Main.getPrefix() + "Der alte Spawn mit der ID " + Main.getColorCode() + spawnNumber + " §7wurde entfernt, um für den neuen Platz zu machen.");
					}

					final Spawn spawn = new Spawn(SpawnType.NUMBERS, spawnNumber, player.getLocation());
					sender.sendMessage(Main.getPrefix() + "Spawn " + Main.getColorCode() + spawn.getNumber() + " §7gesetzt!");
				} else {
					VaroPlayer varoplayer = VaroPlayer.getPlayer(args[1]);
					if(varoplayer == null) {
						sender.sendMessage(Main.getPrefix() + "Spieler oder Zahl nicht gültig!");
						return;
					}

					Spawn oldSpawn = Spawn.getSpawn(varoplayer);
					if(oldSpawn != null) {
						oldSpawn.delete();
						sender.sendMessage(Main.getPrefix() + "Der alte Spawn mit der ID " + Main.getColorCode() + spawnNumber + " §7wurde entfernt, um für den neuen Platz zu machen.");
					}

					Spawn spawn = new Spawn(SpawnType.PLAYER, varoplayer, player.getLocation());
					sender.sendMessage(Main.getPrefix() + "Spielerspawn " + Main.getColorCode() + spawn.getNumber() + " §7für den Spieler " + Main.getColorCode() + spawn.getPlayer().getName() + " §7gesetzt!");
				}
			} else
				sender.sendMessage(Main.getPrefix() + "/varo spawns set [Zahl/Spieler]");
			return;
		} else if(args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Main.getPrefix() + "Not for console!");
				return;
			}

			if(args.length != 2) {
				sender.sendMessage(Main.getPrefix() + "/varo spawns " + args[0] + " [Zahl/Spieler/All]");
				return;
			}

			if(args[1].equalsIgnoreCase("all")) {
				for(Spawn spawn : Spawn.getSpawnsClone()) {
					spawn.delete();
				}

				sender.sendMessage(Main.getPrefix() + "Alle Spawns erfolgreich entfernt!");
				return;
			}

			Player player = (Player) sender;
			int spawnNumber = -1;
			try {
				spawnNumber = Integer.valueOf(args[1]);
				if(!(spawnNumber > 0)) {
					player.sendMessage(Main.getPrefix() + "Spawn Zahl muss positiv sein!");
					return;
				}
			} catch(NumberFormatException e) {}

			Spawn spawn;
			if(spawnNumber != -1) {
				spawn = Spawn.getSpawn(spawnNumber);
				if(spawn == null) {
					sender.sendMessage(Main.getPrefix() + "Spawn mit der ID" + Main.getColorCode() + spawnNumber + " nicht gefunden!");
					return;
				}

				spawn.delete();
				sender.sendMessage(Main.getPrefix() + "Spawn " + Main.getColorCode() + spawn.getNumber() + " §7entfernt!");
			} else {
				VaroPlayer varoplayer = VaroPlayer.getPlayer(args[1]);
				if(varoplayer == null) {
					sender.sendMessage(Main.getPrefix() + "Spieler oder Zahl nicht gültig!");
					return;
				}

				spawn = Spawn.getSpawn(varoplayer);
				if(spawn == null) {
					sender.sendMessage(Main.getPrefix() + "Spawn von dem Spieler " + Main.getColorCode() + varoplayer.getName() + " nicht gefunden!");
					return;
				}

				sender.sendMessage(Main.getPrefix() + "Spawn von " + Main.getColorCode() + varoplayer.getName() + " §7entfernt!");
			}
			spawn.delete();
			return;
		} else if(args[0].equalsIgnoreCase("list")) {
			if(Spawn.getSpawns().isEmpty()) {
				sender.sendMessage(Main.getPrefix() + "Keine Spawns gefunden!");
				return;
			}

			sender.sendMessage(Main.getPrefix() + "§lEine Liste aller " + Main.getColorCode() + "§lSpawns§7§l:");
			for(Spawn spawn : Spawn.getSpawns()) {
				sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Spawn " + spawn.getNumber() + "§7: ");
				sender.sendMessage(Main.getPrefix() + "§7Typ: " + Main.getColorCode() + spawn.getType().toString());
				sender.sendMessage(Main.getPrefix() + "§7Location: X:" + Main.getColorCode() + spawn.getLocation().getBlockX() + " §7Y: " + Main.getColorCode() + spawn.getLocation().getBlockY() + " §7Z: " + Main.getColorCode() + spawn.getLocation().getBlockZ());
				sender.sendMessage(Main.getPrefix() + "§7Spieler: " + Main.getColorCode() + (spawn.getPlayer() != null ? spawn.getPlayer().getName() : "Keinen"));
				sender.sendMessage(Main.getPrefix());
			}
			return;
		} else
			sender.sendMessage(Main.getPrefix() + "Not found!");
	}
}
