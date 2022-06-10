package de.cuuky.varo.command.varo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.StatType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;

public class StatsCommand extends VaroCommand {
    private static final String[] subCommands = { "set", "add", "decrease", "remove", "reset", "odvReset" };
    public StatsCommand() {
        super("stats", "Bearbeiten von Stats", "varo.stats", subCommands, "stat");
    }

    private String doCommand(Function<VaroPlayer, Boolean> execute, String[] args, int start) {
        List<VaroPlayer> players = new ArrayList<>();
        if (args[start].equals("@a"))
            players.addAll(VaroPlayer.getVaroPlayers());
        else {
            for (int i = start; i < args.length; i++) {
                VaroPlayer vp = VaroPlayer.getPlayer(args[i]);
                if (vp == null) continue;
                players.add(VaroPlayer.getPlayer(args[i]));
            }
        }

        if (players.isEmpty()) return null;
        for (VaroPlayer vp : players)
            if (!execute.apply(vp))
                return null;

        return players.stream().map(VaroPlayer::getName).collect(Collectors.joining(", "));
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats §7<Spieler>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label +
                " stats set/add*/decrease* §7<Stat> <Value> <Spieler.../@a> | Setzt einen Stat auf einen bestimmten Wert");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label +
                " stats remove §7<Stat> <Spieler.../@a> | Setzt einen oder alle Wert(e) zurück");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label +
                " stats reset §7<Spieler.../@a> | Resettet alle Stats");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label +
                " stats odvReset §7<Spieler.../@a> | Resettet alles ausser Kills, Wins, Rank, Team und YT-Link");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + " *Nur für Werte nutzbar, die eine Zahl sind");
            sender.sendMessage(Main.getPrefix() + " Tipp: Das '...' bedeutet, dass man mehrere Spieler angeben kann.");
            return;
        }

        if (args.length == 1) {
            VaroPlayer target;
            if ((target = VaroPlayer.getPlayer(args[0])) == null) {
                sender.sendMessage(Main.getPrefix() + "Spieler nicht gefunden!");
                return;
            }

            sender.sendMessage(Main.getPrefix() + "Stats von " + Main.getColorCode() + target.getName() + "§7:");
            sender.sendMessage(Main.getPrefix());
            for (String stat : target.getStats().getStatsListed())
                sender.sendMessage(Main.getPrefix() + stat);
            return;
        }

        if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("add") ||
            args[0].equalsIgnoreCase("decrease")) {
            if (args.length == 2) {
                String stats = Arrays.stream(StatType.values()).map(StatType::getArg).collect(Collectors.joining(", "));
                sender.sendMessage(Main.getPrefix() + "Stats available: " + stats);
                return;
            }

            StatType type = StatType.getByName(args[1]);
            boolean set = !args[0].equalsIgnoreCase("remove");
            int min = set ? 4 : 3;
            if (set) {
                if (args.length < min) {
                    sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label +
                        " stats set/add/decrease §7<Stat...> <Value> <Spieler.../@a> | Setzt einen Stat auf einen bestimmten Wert");
                    return;
                }
            } else {
                if (args.length < min) {
                    sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label +
                        " stats remove §7<Stat> <Spieler.../@a> | Setzt einen oder alle Wert(e) zurück");
                    return;
                }
            }

            if (type == null) {
                sender.sendMessage(
                    Main.getPrefix() + "Stat '" + Main.getColorCode() + args[1] + "§7' konnte nicht gefunden werden!");
                return;
            }

            String found = this.doCommand((target) -> {
                String toSet = args[2];
                boolean add = args[0].equalsIgnoreCase("add"), dec = args[0].equalsIgnoreCase("decrease");
                if (add || dec) {
                    try {
                        int from = Integer.parseInt(String.valueOf(type.get(target))), calc =
                            Integer.parseInt(toSet) * (dec ? -1 : 1);
                        toSet = String.valueOf(from + calc);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Main.getPrefix() + "Konnte Zahlen nicht verrechnen!");
                        return false;
                    }
                }

                if (!args[0].equalsIgnoreCase("remove")) {
                    if (!type.execute(toSet, target)) {
                        sender.sendMessage(Main.getPrefix() + "§7Der Wert '" + Main.getColorCode() + toSet +
                            "§7' §7konnte nicht fuer " + this + " gesetzt werden!");
                    }
                } else type.remove(target);
                return true;
            }, args, min - 1);

            if (found == null)
                sender.sendMessage(Main.getPrefix() + "Keine Spieler gefunden!");
            else
                sender.sendMessage(
                    Main.getPrefix() + "Stat erfolgreich für " + Main.getColorCode() + found + " §7geupdated!");
        } else if (args[0].equalsIgnoreCase("reset")) {
            String found = this.doCommand((target) -> {
                target.getStats().loadDefaults();
                return true;
            }, args, 1);

            if (found == null)
                sender.sendMessage(Main.getPrefix() + "Keine Spieler gefunden!");
            else
                sender.sendMessage(
                    Main.getPrefix() + "Spieler " + Main.getColorCode() + found + "§7 erfolgreich zurueckgesetzt!");
        } else if (args[0].equalsIgnoreCase("odvreset")) {
            String found = this.doCommand((target) -> {
                target.getStats().loadStartDefaults();
                target.getStats().setState(PlayerState.ALIVE);
                if (target.getTeam() != null)
                    target.getTeam().removeMember(target);
                return true;
            }, args, 1);

            sender.sendMessage(
                Main.getPrefix() + "Spieler " + Main.getColorCode() + found + "§7 erfolgreich ODV-zurueckgesetzt!");
        } else
            sender.sendMessage(
                Main.getPrefix() + "Not found! Type " + Main.getColorCode() + label + " stats §7for help.");
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 2 && subCommands != null) {
            List<String> subCommands = Arrays.asList(this.subCommands);
            list.addAll(subCommands);
        }
        if (args.length == 3 && (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("decrease") || args[1].equalsIgnoreCase("remove"))) {
            for (StatType st : StatType.values()) {
                list.add(st.name());
            }
        }  else if (args.length == 3 && (args[1].equalsIgnoreCase("reset") || args[1].equalsIgnoreCase("odvReset"))) {
            String[] subCommands = {"@a"};
            List<String> subCommandsList = Arrays.asList(subCommands);
            list.addAll(subCommandsList);
            for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
                list.add(vp.getName());
            }
        }
        if (args.length == 4 && args[1].equalsIgnoreCase("remove")) {
            String[] subCommands = {"@a"};
            List<String> subCommandsList = Arrays.asList(subCommands);
            list.addAll(subCommandsList);
            for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
                list.add(vp.getName());
            }
        }
        if (args.length == 5 && (args[1].equalsIgnoreCase("set") || args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("decrease")) ) {
            String[] subCommands = {"@a"};
            List<String> subCommandsList = Arrays.asList(subCommands);
            list.addAll(subCommandsList);
            for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
                list.add(vp.getName());
            }
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