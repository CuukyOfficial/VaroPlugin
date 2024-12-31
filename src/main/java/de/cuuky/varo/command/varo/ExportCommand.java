package de.cuuky.varo.command.varo;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.team.VaroTeam;
import de.varoplugin.cfw.configuration.YamlConfigurationUtil;
import io.github.almightysatan.slams.Placeholder;

public class ExportCommand extends VaroCommand {

	public ExportCommand() {
		super("export", "Optionen zum Exportieren", "varo.export", "Exportiert, Teams, Spieler etc.");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			Messages.CATEGORY_HEADER.send(vp, Placeholder.constant("category", "Export"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " export players");
			Messages.CATEGORY_FOOTER.send(vp, Placeholder.constant("category", "Export"));
		}

		else if (args.length == 1 && args[0].equalsIgnoreCase("players")) {
			File file = new File("plugins/Varo/exports/players.yml");
			if (file.exists())
				file.delete();

			YamlConfiguration yaml = YamlConfigurationUtil.loadConfiguration(file);
			ArrayList<VaroPlayer> finished = new ArrayList<>();

			for (VaroPlayer player : VaroPlayer.getVaroPlayers()) {
				if (finished.contains(player))
					continue;

				if (player.getTeam() == null) {
					yaml.set(player.getName() + ".name", player.getName());
					yaml.set(player.getName() + ".youtubeLink", player.getStats().getYoutubeLink() == null ? "/" : player.getStats().getYoutubeLink());
					finished.add(player);
				}
			}

			for (VaroTeam team : VaroTeam.getTeams()) {
				String pref = "#" + team.getName();
				yaml.set(pref + ".id", team.getId());
				for (VaroPlayer teamPl : team.getMember()) {
					if (finished.contains(teamPl))
						continue;

					yaml.set(pref + ".member." + teamPl.getName() + ".name", teamPl.getName());
					yaml.set(pref + ".member." + teamPl.getName() + ".youtubeLink", teamPl.getStats().getYoutubeLink() == null ? "/" : teamPl.getStats().getYoutubeLink());
				}
			}

			YamlConfigurationUtil.save(yaml, file);

			Messages.COMMANDS_VARO_EXPORT_SUCCESSFULL.send(vp, Placeholder.constant("file", "/plugins/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + "/exports/players.yml"));
		} else
		    Messages.COMMANDS_ERROR_USAGE.send(vp, Placeholder.constant("command", "export"));
	}
}