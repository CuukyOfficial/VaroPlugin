package de.cuuky.varo.command.varo;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.cfw.configuration.YamlConfigurationUtil;
import de.cuuky.varo.app.Main;
import de.cuuky.varo.command.VaroCommand;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class ExportCommand extends VaroCommand {

	public ExportCommand() {
		super("export", "Optionen zum Exportieren", "varo.export", "Exportiert, Teams, Spieler etc.");
	}

	@Override
	public void onCommand(CommandSender sender, VaroPlayer vp, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_HEADER.getValue(vp).replace("%category%", "Export"));
			sender.sendMessage(Main.getPrefix() + Main.getColorCode() + "/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + " export players");
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_HELP_FOOTER.getValue(vp));
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

			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_EXPORT_SUCCESSFULL.getValue(vp).replace("%file%", "/plugins/" + ConfigSetting.COMMAND_VARO_NAME.getValueAsString() + "/exports/players.yml"));
		} else
			sender.sendMessage(Main.getPrefix() + ConfigMessages.VARO_COMMANDS_ERROR_USAGE.getValue(vp).replace("%commands%", "export"));
	}
}