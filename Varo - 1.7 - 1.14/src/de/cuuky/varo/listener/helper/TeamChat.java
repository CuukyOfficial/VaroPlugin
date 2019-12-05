package de.cuuky.varo.listener.helper;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.ChatLogger;
import de.cuuky.varo.logger.logger.ChatLogger.ChatLogType;

public class TeamChat {

	public TeamChat(VaroPlayer player, String message) {
		if(player.getTeam() == null) {
			player.sendMessage(Main.getPrefix() + "§7Du bist in keinem Team!");
			return;
		}

		if(message.isEmpty())
			return;

		ChatLogger.getInstance().println(ChatLogType.TEAMCHAT, "#" + player.getTeam().getName() + " | " + player.getName() + " >> " + message);
		for(VaroPlayer pl : player.getTeam().getMember()) {
			if(!pl.isOnline())
				continue;

			pl.sendMessage(ConfigMessages.TEAMCHAT_FORMAT.getValue(player).replaceAll("%message%", message));
		}
	}
}
