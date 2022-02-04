package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RegisteredCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public RegisteredCommand() {
		super("registered", new String[] { "registeredplayers, players" }, "Zeigt alle registrierten Spieler an");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if (VaroPlayer.getVaroPlayers().size() == 0) {
			getDiscordBot().sendMessage("Es sind keine Spieler registriert!", "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		String players = "";
		for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
			if (players.equals(""))
				players = vp.getName();
			else
				players = players + ", " + vp.getName();
		}

		getDiscordBot().sendRawMessage("REGISTERED (" + VaroPlayer.getVaroPlayers().size() + ") \n\n" + players, event.getTextChannel());
	}
}