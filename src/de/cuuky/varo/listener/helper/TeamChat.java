package de.cuuky.varo.listener.helper;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;

public class TeamChat {

	public TeamChat(VaroPlayer player, String message) {
		if (player.getTeam() == null) {
			player.sendMessage(Main.getPrefix() + "§7Du bist in keinem Team!");
			return;
		}

		if (message.isEmpty()) return;
		Main.getDataManager().getVaroLoggerManager().getChatLogger().println(ChatLogType.TEAM_CHAT, player.getPlayer(), "#" + player.getTeam().getName(), message);
		for (VaroPlayer pl : player.getTeam().getMember()) {
			if (!pl.isOnline()) continue;
			pl.sendMessage(ConfigMessages.CHAT_TEAMCHAT_FORMAT).replace("%message%", message).replace("%from%", player.getName());
		}
	}
}