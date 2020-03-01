package de.cuuky.varo.command.varo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.utils.varo.VaroUtils;

public class RandomTeamCommand extends VaroCommand {

	public RandomTeamCommand() {
		super("randomteam", "Gibt allen Spielern, die noch kein Team haben, einen zufälligen Teampartner mit Größe", "varo.randomteam", "rt");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vpsender, Command cmd, String label, String[] args) {
		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + "§7/varo randomTeam <Teamgröße>");
			return;
		}

		int teamsize = 0;
		try {
			teamsize = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + args[0] + " §7ist keine Zahl!");
			return;
		}

		if(teamsize < 1) {
			sender.sendMessage(Main.getPrefix() + "§7Die Teamgröße muss mindestens 1 betragen.");
			return;
		} else {
			VaroUtils.doRandomTeam(teamsize);
		}

		sender.sendMessage(Main.getPrefix() + "§7Alle Spieler, die ohne Teams waren, sind nun in " + Main.getColorCode() + teamsize + "§7er Teams!");
	}
}