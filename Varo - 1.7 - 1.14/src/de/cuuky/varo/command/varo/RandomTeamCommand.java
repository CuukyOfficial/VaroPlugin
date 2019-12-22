package de.cuuky.varo.command.varo;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.Team;

public class RandomTeamCommand extends VaroCommand {

	public RandomTeamCommand() {
		super("randomteam", "Gibt allen Spielern, die noch kein Team haben, einen zufälligen Teampartner mit Größe", "varo.randomteam", "rt");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vpsender, Command cmd, String label, String[] args) {
		if (args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/varo randomTeam <Teamgröße>");
			return;
		}

		int teamsize = 0;
		try {
			teamsize = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7ist keine Zahl!");
			return;
		}

		if (teamsize < 1) {
			sender.sendMessage(Main.getPrefix() + "§7Die Teamgröße muss mindestens 1 betragen.");
			return;
		} else {
			doRandomTeam(teamsize);
		}

		sender.sendMessage(Main.getPrefix() + "§7Alle Spieler, die ohne Teams waren, sind nun in " + Main.getColorCode() + teamsize + "§7er Teams!");
	}

	public void doRandomTeam(int teamSize) {
		if (teamSize >= 2) {
			ArrayList<VaroPlayer> finished = new ArrayList<>();
			for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if (finished.contains(vp) || vp.getStats().isSpectator() || vp.getTeam() != null)
					continue;

				ArrayList<VaroPlayer> teamMember = new ArrayList<>();
				teamMember.add(vp);
				finished.add(vp);

				int missingMember = teamSize - 1;
				for (VaroPlayer othervp : VaroPlayer.getOnlinePlayer()) {
					if (missingMember == 0)
						break;

					if (finished.contains(othervp) || othervp.getStats().isSpectator() || othervp.getTeam() != null)
						continue;

					teamMember.add(othervp);
					finished.add(othervp);
					missingMember--;
				}

				if (teamMember.size() != teamSize)
					vp.getPlayer().sendMessage(Main.getPrefix() + "§7Für dich wurden nicht genug" + Main.getColorCode() + " Teampartner §7gefunden!");

				String teamName = "";
				for (VaroPlayer teamPl : teamMember)
					teamName = teamName + teamPl.getName().substring(0, teamPl.getName().length() / teamSize);

				Team team = new Team(teamName);
				for (VaroPlayer teamPl : teamMember)
					team.addMember(teamPl);
			}
		} else if (teamSize == 1) {
			for (VaroPlayer pl : VaroPlayer.getOnlinePlayer()) {
				if (pl.getTeam() != null || pl.getStats().isSpectator())
					continue;

				new Team(pl.getName().length() == 16 ? pl.getName().substring(0, 15) : pl.getName()).addMember(pl);
			}
		}
	}
}