package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class OnlineCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public OnlineCommand() {
		super("online", new String[] { "onlineplayers" }, "Zeigt alle Spieler an, die online sind");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if (VaroPlayer.getOnlinePlayer().size() == 0) {
			getDiscordBot().sendMessage("Es sind keine Spieler online!", "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		String players = "";
		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			String name = vp.getStats().getState() == PlayerState.SPECTATOR ? vp.getName() + " (SPECTATOR)" : vp.getName();
			if (players.equals(""))
				players = name;
			else
				players = players + ", " + name;
		}

		getDiscordBot().sendRawMessage("ONLINE (" + VaroPlayer.getOnlinePlayer().size() + ") \n\n" + players, event.getTextChannel());
	}
}