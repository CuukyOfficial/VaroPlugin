package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RemainingCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public RemainingCommand() {
		super("remaining", new String[] { "remainingplayers", "alive" }, "Zeigt alle verbleibenden Spieler an");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if(VaroPlayer.getAlivePlayer().size() == 0) {
			getDiscordBot().sendMessage("Es sind keine Spieler am leben!", "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		String players = "";
		for(VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			if(players.equals(""))
				players = vp.getName();
			else
				players = players + ", " + vp.getName();
		}

		getDiscordBot().sendRawMessage("ALIVE (" + VaroPlayer.getAlivePlayer().size() + ") \n\n" + players, event.getTextChannel());
	}
}
