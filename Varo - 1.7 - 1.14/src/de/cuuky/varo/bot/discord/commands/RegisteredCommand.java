package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;

public class RegisteredCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public RegisteredCommand() {
		super("registered", new String[]{"registeredplayers, players"}, "Zeigt alle registrierten Spieler an");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if (VaroPlayer.getVaroPlayer().size() == 0) {
			getDiscordBot().sendMessage("Es sind keine Spieler registriert!", "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		String players = "";
		for (VaroPlayer vp : VaroPlayer.getVaroPlayer()) {
			if (players.equals(""))
				players = vp.getName();
			else
				players = players + ", " + vp.getName();
		}

		getDiscordBot().sendRawMessage("REGISTERED (" + VaroPlayer.getVaroPlayer().size() + ") \n\n" + players, event.getTextChannel());
	}
}
