package de.varoplugin.varo.listener.helper;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.logger.logger.ChatLogger.ChatLogType;
import de.varoplugin.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;

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
			Messages.CHAT_TEAM.send(pl, player, Placeholder.constant("message", message));
		}
	}
}