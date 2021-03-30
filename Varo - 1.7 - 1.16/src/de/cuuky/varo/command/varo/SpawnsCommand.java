package de.cuuky.varo.command.varo;

import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.cfw.version.types.Materials;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.world.generators.SpawnGenerator;
import de.cuuky.varo.spawns.Spawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpawnsCommand extends VaroCommand {

    public SpawnsCommand() {
        super("spawns", "Hauptbefehl fuer die Spawns, in welchen die Spieler spawnen", "varo.spawns", "spawnholes", "spawn", "holes");
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "§lSpawn Command§7§l:");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 set <Zahl/Spieler>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 delete <Zahl/Spieler> - (Loescht den Spawneintrag und den Spawn in der Welt)");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 player <Zahl> <set/remove> [Spieler/@a]");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 list");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 generate <radius>/auto <amount>/player/team [Half-Step-Materiall] [Side-Block-Material]");
            sender.sendMessage(Main.getPrefix() + "------");
            sender.sendMessage(Main.getPrefix() + "/varo spawns generate kann dir deine Spawns entweder nach Anzahl oder den eingetragenen Teams oder Spielern generieren.\n" + "Der Unterschied zwischen 'player' und 'team' ist, dass bei 'team' auch die Teams bei der Sortierung beruecksichtigt werden.");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Example for number: §7/varo spawns generate 30 40 STONE_SLAB");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Example for players: §7/varo spawns generate auto team");
            sender.sendMessage(Main.getPrefix() + "------");
            return;
        }

        if (args[0].equalsIgnoreCase("generate")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getPrefix() + "Du musst ein Spieler sein!");
                return;
            }

            if (args.length < 3) {
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 generate <radius>/auto <amount>/player/team [Half-Step-Materiall] [Side-Block-Material]");
                return;
            }

            String material = null;
            if (args.length >= 4)
                material = args[3];

            String sideBlockMaterial = null;
            if (args.length >= 5)
                sideBlockMaterial = args[4];

            if (material != null && Materials.fromString(material) == null || sideBlockMaterial != null && Materials.fromString(sideBlockMaterial) == null ) {
                sender.sendMessage(Main.getPrefix() + "ID's der Blöcke nicht gefunden!");
                return;
            }

            try {
                Integer.valueOf(args[1]);
            } catch (Exception e) {
                try {
                    if (args[1].equalsIgnoreCase("auto") || args[1].equalsIgnoreCase("automatic")) {
                        if (args[2].equalsIgnoreCase("player") || args[2].equalsIgnoreCase("team"))
                            args[1] = String.valueOf((int) (VaroPlayer.getAlivePlayer().size() * 0.85));
                        else
                            args[1] = String.valueOf((int) (Integer.parseInt(args[2]) * 0.85));
                    }
                } catch (Exception e1) {
                    sender.sendMessage(Main.getPrefix() + "Beim Erstellen der Spawns ist ein Fehler aufgetreten! Richtige Werte angegeben?");
                    e1.printStackTrace();
                    return;
                }
            }

            try {
                if (args[2].equalsIgnoreCase("player") || args[2].equalsIgnoreCase("team"))
                    new SpawnGenerator(((Player) sender).getLocation(), Integer.parseInt(args[1]), args[2].equalsIgnoreCase("team"), material, sideBlockMaterial);
                else
                    new SpawnGenerator(((Player) sender).getLocation(), Integer.parseInt(args[1]), Integer.parseInt(args[2]), material, sideBlockMaterial);
            } catch (Exception e) {
                sender.sendMessage(Main.getPrefix() + "Beim Erstellen der Spawns ist ein Fehler aufgetreten! Richtige Werte angegeben?");
                e.printStackTrace();
                return;
            }

            sender.sendMessage(Main.getPrefix() + "§7Die Spawns wurden mit der Anzahl " + Main.getColorCode() + args[2] + "§7, dem Radius " + Main.getColorCode() + args[1] + "§7, dem Block-Material " + Main.getColorCode() + (args.length >= 4 ? args[3] : "STONE_BRICK_SLAB") + " §7und dem Seitenblock-Material " + Main.getColorCode() + (args.length >= 5 ? args[4] : "DIRT") + " §7generiert!");
        } else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("place")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getPrefix() + "Not for console!");
                return;
            }

            Player player = (Player) sender;
            if (args.length == 1) {
                Spawn spawn = new Spawn(player.getLocation());
                player.sendMessage(Main.getPrefix() + Main.getColorCode() + "Spawn " + spawn.getNumber() + " §7erfolgreich erstellt!");
            } else if (args.length == 2) {
                int spawnNumber = -1;
                try {
                    spawnNumber = Integer.parseInt(args[1]);
                    if (!(spawnNumber > 0)) {
                        player.sendMessage(Main.getPrefix() + "Spawn Zahl muss positiv sein!");
                        return;
                    }
                } catch (NumberFormatException ignored) {
                }

                if (spawnNumber != -1) {
                    Spawn oldSpawn = Spawn.getSpawn(spawnNumber);
                    if (oldSpawn != null) {
                        oldSpawn.delete();
                        sender.sendMessage(Main.getPrefix() + "Der alte Spawn mit der ID " + Main.getColorCode() + spawnNumber + " §7wurde entfernt, um fuer den neuen Platz zu machen.");
                    }

                    Spawn spawn = new Spawn(spawnNumber, player.getLocation());
                    sender.sendMessage(Main.getPrefix() + "Spawn " + Main.getColorCode() + spawn.getNumber() + " §7gesetzt!");
                } else {
                    VaroPlayer varoplayer = VaroPlayer.getPlayer(args[1]);
                    if (varoplayer == null) {
                        sender.sendMessage(Main.getPrefix() + "Spieler oder Zahl nicht gueltig!");
                        return;
                    }

                    Spawn oldSpawn = Spawn.getSpawn(varoplayer);
                    if (oldSpawn != null) {
                        oldSpawn.delete();
                        sender.sendMessage(Main.getPrefix() + "Der alte Spawn mit der ID " + Main.getColorCode() + oldSpawn.getNumber() + " §7wurde entfernt, um fuer den neuen Platz zu machen.");
                    }

                    Spawn spawn = new Spawn(varoplayer, player.getLocation());
                    sender.sendMessage(Main.getPrefix() + "Spielerspawn " + Main.getColorCode() + spawn.getNumber() + " §7fuer den Spieler " + Main.getColorCode() + spawn.getPlayer().getName() + " §7gesetzt!");
                }
            } else
                sender.sendMessage(Main.getPrefix() + "/varo spawns set [Zahl/Spieler]");
        } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Main.getPrefix() + "Not for console!");
                return;
            }

            if (args.length != 2) {
                sender.sendMessage(Main.getPrefix() + "/varo spawns " + args[0] + " [Zahl/Spieler/@a]");
                return;
            }

            if (args[1].equalsIgnoreCase("@a")) {
                for (Spawn spawn : Spawn.getSpawnsClone())
                    spawn.delete();

                sender.sendMessage(Main.getPrefix() + "Alle Spawns erfolgreich entfernt!");
                return;
            }

            Player player = (Player) sender;
            int spawnNumber = -1;
            try {
                spawnNumber = Integer.parseInt(args[1]);
                if (!(spawnNumber > 0)) {
                    player.sendMessage(Main.getPrefix() + "Spawn Zahl muss positiv sein!");
                    return;
                }
            } catch (NumberFormatException ignored) {
            }

            Spawn spawn;
            if (spawnNumber != -1) {
                spawn = Spawn.getSpawn(spawnNumber);
                if (spawn == null) {
                    sender.sendMessage(Main.getPrefix() + "Spawn mit der ID" + Main.getColorCode() + spawnNumber + " nicht gefunden!");
                    return;
                }

                spawn.delete();
                sender.sendMessage(Main.getPrefix() + "Spawn " + Main.getColorCode() + spawn.getNumber() + " §7entfernt!");
            } else {
                VaroPlayer varoplayer = VaroPlayer.getPlayer(args[1]);
                if (varoplayer == null) {
                    sender.sendMessage(Main.getPrefix() + "Spieler oder Zahl nicht gueltig!");
                    return;
                }

                spawn = Spawn.getSpawn(varoplayer);
                if (spawn == null) {
                    sender.sendMessage(Main.getPrefix() + "Spawn von dem Spieler " + Main.getColorCode() + varoplayer.getName() + " nicht gefunden!");
                    return;
                }

                sender.sendMessage(Main.getPrefix() + "Spawn von " + Main.getColorCode() + varoplayer.getName() + " §7entfernt!");
            }
            spawn.delete();
        } else if (args[0].equalsIgnoreCase("player")) {
            if (args.length < 3) {
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 player <Zahl/@a> <set/remove> [Spieler]");
                return;
            }

            int spawnNumber = -1;
            try {
                spawnNumber = Integer.parseInt(args[1]);
                if (!(spawnNumber > 0)) {
                    sender.sendMessage(Main.getPrefix() + "Spawn Zahl muss positiv sein!");
                    return;
                }
            } catch (NumberFormatException ignored) {
            }

            Spawn spawn = Spawn.getSpawn(spawnNumber);
            if (spawn == null && (!args[1].equals("@a") && args[2].equalsIgnoreCase("set"))) {
                sender.sendMessage(Main.getPrefix() + "Spawn konnte nicht gefunden werden!");
                return;
            }

            if (args[2].equalsIgnoreCase("set")) {
                if (args.length < 4) {
                    sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo spawns§7 player <Zahl> set [Spieler]");
                    return;
                }

                VaroPlayer varoplayer = VaroPlayer.getPlayer(args[3]);
                if (varoplayer == null) {
                    sender.sendMessage(Main.getPrefix() + "Spieler oder Zahl nicht gueltig!");
                    return;
                }

                spawn.setPlayer(varoplayer);
                sender.sendMessage(Main.getPrefix() + "Spieler von Spawn " + Main.getColorCode() + spawn.getNumber() + " §7erfolgreich auf " + Main.getColorCode() + spawn.getPlayer().getName() + " §7gesetzt!");
            } else if (args[2].equalsIgnoreCase("remove")) {
                if (args[1].equals("@a")) {
                    for (Spawn spaw : Spawn.getSpawns())
                        spaw.setPlayer(null);

                    sender.sendMessage(Main.getPrefix() + "Spieler von allen Spawns entfernt!");
                    return;
                }

                spawn.setPlayer(null);

                sender.sendMessage(Main.getPrefix() + "Spieler von Spawn " + Main.getColorCode() + spawn.getNumber() + " §7erfolgreich entfernt!");
            }

        } else if (args[0].equalsIgnoreCase("list")) {
            if (Spawn.getSpawns().isEmpty()) {
                sender.sendMessage(Main.getPrefix() + "Keine Spawns gefunden!");
                return;
            }

            sender.sendMessage(Main.getPrefix() + "§lEine Liste aller " + Main.getColorCode() + "§lSpawns§7§l:");
            for (Spawn spawn : Spawn.getSpawns()) {
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "Spawn " + spawn.getNumber() + "§7: ");
                sender.sendMessage(Main.getPrefix() + "§7Location: " + new LocationFormat(spawn.getLocation()).format("§7X§8: " + Main.getColorCode() + "x §7Y§8: " + Main.getColorCode() + "y §7Z§8: " + Main.getColorCode() + "z §7in " + Main.getColorCode() + "world"));
                sender.sendMessage(Main.getPrefix() + "§7Spieler: " + Main.getColorCode() + (spawn.getPlayer() != null ? spawn.getPlayer().getName() : "Keinen"));
                sender.sendMessage(Main.getPrefix());
            }
        } else
            sender.sendMessage(Main.getPrefix() + "Not found! /varo spawns");
    }
}