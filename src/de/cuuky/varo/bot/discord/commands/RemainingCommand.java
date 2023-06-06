package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class RemainingCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public RemainingCommand() {
		super("alive", "Zeigt alle verbleibenden Spieler an");
	}

	@Override
	public void onExecute(SlashCommandInteraction event) {
	    String players = "";
		for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			if (players.equals(""))
				players = vp.getName();
			else
				players = players + ", " + vp.getName();
		}

		getDiscordBot().reply(players, "Alive (" + VaroPlayer.getAlivePlayer().size() + ")", Color.BLUE, event);
	}
}