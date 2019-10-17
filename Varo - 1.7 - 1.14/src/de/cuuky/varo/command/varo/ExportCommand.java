package de.cuuky.varo.command.varo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.Team;

public class ExportCommand extends VaroCommand {

	public ExportCommand() {
		super("export", "Optionen zum Exportieren", "varo.export", "Exportiert, Teams, Spieler etc.");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			sender.sendMessage(Main.getPrefix() + "§7----- §l" + Main.getColorCode() + "Export" + "§7 -----");
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo export players | Exportiert alle Spieler");
		}

		if(args.length != 1) {
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/varo export players | Exportiert alle Spieler");
			return;
		}

		if(args[0].equalsIgnoreCase("players")) {
			File file = new File("plugins/Varo/exports/players.yml");
			if(file.exists())
				file.delete();

			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			ArrayList<VaroPlayer> finished = new ArrayList<>();

			for(VaroPlayer player : VaroPlayer.getVaroPlayer()) {
				if(finished.contains(player))
					continue;

				if(player.getTeam() == null) {
					yaml.set(player.getName() + ".name", player.getName());
					yaml.set(player.getName() + ".youtubeLink", player.getStats().getYoutubeLink() == null ? "/" : player.getStats().getYoutubeLink());
					finished.add(player);
				}
			}

			for(Team team : Team.getTeams()) {
				String pref = "#" + team.getName();
				yaml.set(pref + ".id", team.getId());
				for(VaroPlayer teamPl : team.getMember()) {
					if(finished.contains(teamPl))
						continue;

					yaml.set(pref + ".member." + teamPl.getName() + ".name", teamPl.getName());
					yaml.set(pref + ".member." + teamPl.getName() + ".youtubeLink", teamPl.getStats().getYoutubeLink() == null ? "/" : teamPl.getStats().getYoutubeLink());
				}
			}

			try {
				yaml.save(file);
			} catch(IOException e) {
				e.printStackTrace();
			}

			sender.sendMessage(Main.getPrefix() + "Successfully saved to '/plugins/Varo/exports/players.yml'!");
		} else
			sender.sendMessage(Main.getPrefix() + "Command not found.");
	}
}
