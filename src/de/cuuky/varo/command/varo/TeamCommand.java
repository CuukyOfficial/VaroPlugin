package de.cuuky.varo.command.varo;

import de.cuuky.cfw.utils.UUIDUtils;
import de.cuuky.cfw.utils.chat.PageableChatBuilder;
import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroChatListMessages;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.gui.team.TeamGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TeamCommand extends VaroCommand {

    private PageableChatBuilder<VaroTeam> listBuilder;

    public TeamCommand() {
        super("team", "Hauptcommand fuer das Managen der Teams", "varo.teams", "teams");

        this.listBuilder = new PageableChatBuilder<>(VaroTeam::getTeams)
                .messages(new VaroChatListMessages<>(team -> {
                    StringBuilder message = new StringBuilder();
                    message.append(Main.getPrefix() + Main.getColorCode() + " §l" +
                            team.getId() + "§7; " + Main.getColorCode() + team.getName() + "\n");
                    String list = new ArrayList<>(team.getMember()).stream()
                            .map(VaroPlayer::getName).collect(Collectors.joining(", "));
                    message.append(Main.getPrefix() + "  " + list + "\n");
                    message.append(Main.getPrefix());
                    return message.toString();
                }, "/varo team list", "List aller Teams"));
    }

    @Override
    public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Main.getPrefix() + Main.getProjectName() + " §7Team setup Befehle:");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team §7<Team/TeamID>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team create §7<Team/TeamID> <Spieler 1, 2, 3...>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team remove §7<Team/TeamID/Player/@a>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team add §7<Team/TeamID> <Player>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team rename §7<Team/TeamID> <Neuer Team-Name>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team colorcode §7<Team/TeamID> remove/<Farbcode>");
            sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team list");
            return;
        }

        VaroTeam vteam = VaroTeam.getTeam(args[0]);
        if (vteam != null && sender instanceof Player) {
            new TeamGUI((Player) sender, vteam);
            return;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!(args.length >= 2)) {
                sender.sendMessage(Main.getPrefix() + "/varo team create <Teamname> [Spieler 1, Spieler 2, Spieler 3...]");
                return;
            }

            if (!args[1].matches(VaroTeam.NAME_REGEX)) {
                sender.sendMessage(Main.getPrefix() + "Teamname nicht valide!");
                return;
            }

            VaroTeam team = VaroTeam.getTeam(args[1]);

            if (team != null) {
                boolean teamIdentical = true;
                for (int i = 2; i < args.length; i++) {
                    VaroPlayer player = VaroPlayer.getPlayer(args[i]);
                    if (!team.getMember().contains(player) || player == null) {
                        teamIdentical = false;
                    }
                }
                if (teamIdentical) {
                    sender.sendMessage(Main.getPrefix() + "Dieses Team ist bereits registriert.");
                } else {
                    sender.sendMessage(Main.getPrefix() + "§cDas Team konnte nicht registriert werden, der Teamname ist bereits belegt.");
                }
                return;
            }

            team = new VaroTeam(args[1]);
            sender.sendMessage(Main.getPrefix() + "Team " + Main.getColorCode() + team.getName() + " §7mit der ID " + Main.getColorCode() + team.getId() + " §7erfolgreich erstellt!");

            for (int i = 2; i < args.length; i++) {
                String arg = args[i];

                VaroPlayer varoplayer = VaroPlayer.getPlayer(arg);
                if (varoplayer == null) {
                    String uuid;
                    try {
                        uuid = Main.getInstance().getUUID(arg).toString();
                    } catch (Exception e) {
                        sender.sendMessage(Main.getPrefix() + "§c" + arg + " wurde nicht gefunden.");
                        String newName;
                        try {
                            newName = UUIDUtils.getNamesChanged(arg);
                            sender.sendMessage(Main.getPrefix() + "§cEin Spieler, der in den letzten 30 Tagen " + arg + " hiess, hat sich in §7" + newName + " §cumbenannt.");
                            sender.sendMessage(Main.getPrefix() + "Benutze \"/varo team add\", um diese Person einem Team hinzuzufuegen.");
                        } catch (Exception f) {
                            sender.sendMessage(Main.getPrefix() + "§cIn den letzten 30 Tagen gab es keinen Spieler mit diesem Namen.");
                        }
                        continue;
                    }

                    varoplayer = new VaroPlayer(arg, uuid);
                }

                team.addMember(varoplayer);
                sender.sendMessage(Main.getPrefix() + "Spieler " + Main.getColorCode() + varoplayer.getName() + " §7erfolgreich hinzugefuegt!");
            }
            return;
        } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
            if (args.length != 2) {
                sender.sendMessage(Main.getPrefix() + "/varo team remove <Team/TeamID/Player>");
                return;
            }

            VaroTeam team = VaroTeam.getTeam(args[1]);
            VaroPlayer varoplayer = VaroPlayer.getPlayer(args[1]);

            if (team != null) {
                team.delete();
                sender.sendMessage(Main.getPrefix() + "Team erfolgreich geloescht!");
            } else if (varoplayer != null) {
                varoplayer.getTeam().removeMember(varoplayer);
                sender.sendMessage(Main.getPrefix() + "Spieler " + Main.getColorCode() + varoplayer.getName() + " §7erfolgreich aus seinem Team entfernt!");
            } else if (args[1].equalsIgnoreCase("@a")) {
                while (VaroTeam.getTeams().size() > 0) {
                    VaroTeam.getTeams().get(0).delete();
                }
                sender.sendMessage(Main.getPrefix() + "Alle Teams erfolgreich geloescht!");
            } else
                sender.sendMessage(Main.getPrefix() + "Team, TeamID oder Spieler nicht gefunden!");
            return;
        } else if (args[0].equalsIgnoreCase("list")) {
            this.listBuilder.page(args.length >= 2 ? args[1] : "1").build().send(sender);
        } else if (args[0].equalsIgnoreCase("rename")) {
            if (args.length != 3) {
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team rename §7<Team/TeamID> <Team>");
                return;
            }

            if (!args[2].matches(VaroTeam.NAME_REGEX)) {
                sender.sendMessage(Main.getPrefix() + "Ungültiger teamname!");
                return;
            }

            VaroTeam team = VaroTeam.getTeam(args[1]);

            if (team == null) {
                sender.sendMessage(Main.getPrefix() + "Team nicht gefunden!");
                return;
            }

            team.setName(args[2]);
            sender.sendMessage(Main.getPrefix() + "Das Team " + Main.getColorCode() + args[1] + " §7wurde erfolgreich in " + Main.getColorCode() + team.getName() + " §7umbenannt!");
        } else if (args[0].equalsIgnoreCase("add")) {
            if (args.length != 3) {
                sender.sendMessage(Main.getPrefix() + "/varo team add <Team/TeamID> <Player>");
                return;
            }

            VaroPlayer varoplayer = VaroPlayer.getPlayer(args[2]);
            VaroTeam team = VaroTeam.getTeam(args[1]);

            if (team == null) {
                sender.sendMessage(Main.getPrefix() + "Team nicht gefunden!");
                return;
            }

            if (varoplayer == null) {
                String uuid = null;
                try {
                    uuid = Main.getInstance().getUUID(args[2]).toString();
                } catch (Exception e) {
                    sender.sendMessage(Main.getPrefix() + args[2] + " besitzt keinen Minecraft-Account!");
                    return;
                }

                varoplayer = new VaroPlayer(args[2], uuid);
            }

            if (varoplayer.getTeam() != null) {
                if (varoplayer.getTeam().equals(team)) {
                    sender.sendMessage(Main.getPrefix() + "Dieser Spieler ist bereits in diesem Team!");
                    return;
                }

                varoplayer.getTeam().removeMember(varoplayer);
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + varoplayer.getName() + " §7wurde aus seinem jetzigen Team entfernt!");
            }

            team.addMember(varoplayer);
            sender.sendMessage(Main.getPrefix() + "Spieler " + Main.getColorCode() + varoplayer.getName() + " §7erfolgreich in das Team " + Main.getColorCode() + team.getName() + " §7gesetzt!");
            return;
        } else if (args[0].equalsIgnoreCase("colorcode")) {
            if (args.length != 3) {
                sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo team colorcode §7<Team/TeamID> remove/<Farbcode>");
                return;
            }

            VaroTeam team = VaroTeam.getTeam(args[1]);
            if (team == null) {
                sender.sendMessage(Main.getPrefix() + "Team nicht gefunden!");
                return;
            }

            if (args[2].equalsIgnoreCase("remove")) {
                team.setColorCode(null);
                sender.sendMessage(Main.getPrefix() + "Team-Farbcode vom Team " + team.getDisplay() + " §7erfolgreich entfernt");
                return;
            }

            team.setColorCode(args[2]);
            sender.sendMessage(Main.getPrefix() + "Team-Farbcode vom Team " + team.getDisplay() + " §7erfolgreich geaendert!");
        } else
            sender.sendMessage(Main.getPrefix() + "§7Befehl nicht gefunden! " + Main.getColorCode() + "/team");
        return;
    }
}
