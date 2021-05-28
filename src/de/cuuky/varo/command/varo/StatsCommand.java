package de.cuuky.varo.command.varo;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatsCommand extends VaroCommand {

    interface ArgumentReceiver {

        void receive(String value, VaroPlayer vp);

    }

    public enum SetArgumentType {

        ADMIN_IGNORE("adminignore", (value, vp) -> vp.setAdminIgnore(Boolean.parseBoolean(value)), VaroPlayer::isAdminIgnore, (vp) -> vp.setAdminIgnore(false)),
        COUNTDOWN("countdown", (value, vp) -> vp.getStats().setCountdown(Integer.parseInt(value)), vp -> vp.getStats().getCountdown(), (vp) -> vp.getStats().setCountdown(ConfigSetting.PLAY_TIME.getValueAsInt() * 60)),
        EPISODES_PLAYED("episodesplayed", (value, vp) -> vp.getStats().setSessionsPlayed(Integer.parseInt(value)), vp -> vp.getStats().getSessionsPlayed(), (vp) -> vp.getStats().setSessionsPlayed(0)),
        KILLS("kills", (value, vp) -> vp.getStats().setKills(Integer.parseInt(value)), vp -> vp.getStats().getKills(), (vp) -> vp.getStats().setKills(0)),
        PLAYER_STATE("playerstate", (value, vp) -> vp.getStats().setState(PlayerState.getByName(value)), vp -> vp.getStats().getState(), (vp) -> vp.getStats().setState(PlayerState.ALIVE)),
        RANK("rank", (value, vp) -> vp.setRank(new Rank(value)), vp -> vp.getRank().getDisplay(), (vp) -> vp.setRank(null)),
        SESSIONS("sessions", (value, vp) -> vp.getStats().setSessions(Integer.parseInt(value)), vp -> vp.getStats().getSessions(), (vp) -> vp.getStats().setSessions(ConfigSetting.SESSIONS_PER_DAY.getValueAsInt())),
        WINS("wins", (value, vp) -> vp.getStats().setWins(Integer.parseInt(value)), vp -> vp.getStats().getWins(), (vp) -> vp.getStats().setWins(0)),
        YOUTUBE_LINK("youtubelink", (value, vp) -> vp.getStats().setYoutubeLink(value), vp -> vp.getStats().getYoutubeLink(), (vp) -> vp.getStats().setYoutubeLink(null));

        private final String arg;
        private final ArgumentReceiver receiver;
        private final Function<VaroPlayer, Object> getter;
        private final Consumer<VaroPlayer> reset;

        SetArgumentType(String arg, ArgumentReceiver receiver, Function<VaroPlayer, Object> getter, Consumer<VaroPlayer> reset) {
            this.arg = arg;
            this.receiver = receiver;
            this.getter = getter;
            this.reset = reset;
        }

        public void execute(String value, VaroPlayer vp, CommandSender sender) {
            try {
                this.receiver.receive(value, vp);
                if (vp.isOnline())
                    vp.update();
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(Main.getPrefix() + "§7Der Wert '" + Main.getColorCode() + value + "§7' §7konnte nicht fuer " + this.toString() + " gesetzt werden!");
            }
        }

        public void remove(VaroPlayer vp) {
            this.reset.accept(vp);
            if (vp.isOnline())
                vp.update();
        }

        public String getArg() {
            return this.arg;
        }

        public Object get(VaroPlayer vp) {
            return this.getter.apply(vp);
        }

        public static SetArgumentType getByName(String name) {
            for (SetArgumentType type : values())
                if (type.getArg().equalsIgnoreCase(name))
                    return type;

            return null;
        }
    }

    public StatsCommand() {
        super("stats", "Bearbeiten von Stats", "varo.stats", "stat");
    }

    private String doCommand(Function<VaroPlayer, Boolean> execute, String[] args, int start) {
        List<VaroPlayer> players = new ArrayList<>();
        if (args[start].equals("@a"))
            players.addAll(VaroPlayer.getVaroPlayer());
        else {
            for (int i = start; i < args.length; i++) {
                VaroPlayer vp = VaroPlayer.getPlayer(args[i]);
                if (vp == null)
                    continue;

                players.add(VaroPlayer.getPlayer(args[i]));
            }
        }

        if (players.isEmpty())
            return null;

        for (VaroPlayer vp : players)
            if (!execute.apply(vp))
                return null;

        return players.stream().map(VaroPlayer::getName).collect(Collectors.joining(", "));
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats §7<Spieler>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats set/add*/decrease* §7<Stat> <Value> <Spieler.../@a> | Setzt einen Stat auf einen bestimmten Wert");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats remove §7<Stat> <Spieler.../@a> | Setzt einen oder alle Wert(e) zurück");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats reset §7<Spieler.../@a> | Resettet alle Stats");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats odvReset §7<Spieler.../@a> | Resettet alles ausser Kills, Wins, Rank, Team und YT-Link");
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

        if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("decrease")) {
            if (args.length == 2) {
                String stats = Arrays.stream(SetArgumentType.values()).map(SetArgumentType::getArg).collect(Collectors.joining(", "));
                sender.sendMessage(Main.getPrefix() + "Stats available: " + stats);
                return;
            }

            SetArgumentType type = SetArgumentType.getByName(args[1]);
            boolean set = !args[0].equalsIgnoreCase("remove");
            int min = set ? 4 : 3;
            if (set) {
                if (args.length < min) {
                    sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats set/add/decrease §7<Stat...> <Value> <Spieler.../@a> | Setzt einen Stat auf einen bestimmten Wert");
                    return;
                }
            } else {
                if (args.length < min) {
                    sender.sendMessage(Main.getPrefix() + Main.getColorCode() + label + " stats remove §7<Stat> <Spieler.../@a> | Setzt einen oder alle Wert(e) zurück");
                    return;
                }
            }

            if (type == null) {
                sender.sendMessage(Main.getPrefix() + "Stat '" + Main.getColorCode() + args[1] + "§7' konnte nicht gefunden werden!");
                return;
            }

            String found = this.doCommand((target) -> {
                String toSet = args[2];
                boolean add = args[0].equalsIgnoreCase("add"), dec = args[0].equalsIgnoreCase("decrease");
                if (add || dec) {
                    try {
                        int from = Integer.parseInt(String.valueOf(type.get(target))), calc = Integer.parseInt(toSet) * (dec ? -1 : 1);
                        toSet = String.valueOf(from + calc);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Main.getPrefix() + "Konnte Zahlen nicht verrechnen!");
                        return false;
                    }
                }

                if (!args[0].equalsIgnoreCase("remove"))
                    type.execute(toSet, target, sender);
                else type.remove(target);
                return true;
            }, args, min - 1);

            if (found == null)
                sender.sendMessage(Main.getPrefix() + "Keine Spieler gefunden!");
            else
                sender.sendMessage(Main.getPrefix() + "Stat erfolgreich für " + Main.getColorCode() + found + " §7geupdated!");
        } else if (args[0].equalsIgnoreCase("reset")) {
            String found = this.doCommand((target) -> {
                target.getStats().loadDefaults();
                return true;
            }, args, 1);

            if (found == null)
                sender.sendMessage(Main.getPrefix() + "Keine Spieler gefunden!");
            else
                sender.sendMessage(Main.getPrefix() + "Spieler " + Main.getColorCode() + found + "§7 erfolgreich zurueckgesetzt!");
        } else if (args[0].equalsIgnoreCase("odvreset")) {
            String found = this.doCommand((target) -> {
                target.getStats().loadStartDefaults();
                target.getStats().setState(PlayerState.ALIVE);
                if (target.getTeam() != null)
                    target.getTeam().removeMember(target);
                return true;
            }, args, 1);

            sender.sendMessage(Main.getPrefix() + "Spieler " + Main.getColorCode() + found + "§7 erfolgreich ODV-zurueckgesetzt!");
        } else
            sender.sendMessage(Main.getPrefix() + "Not found! Type " + Main.getColorCode() + label + " stats §7for help.");
    }
}