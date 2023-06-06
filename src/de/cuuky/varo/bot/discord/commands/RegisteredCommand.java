package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class RegisteredCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public RegisteredCommand() {
		super("participants", "Zeigt alle registrierten Spieler an");
	}

	@Override
	public void onExecute(SlashCommandInteraction event) {
		String players = "";
		for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
			if (players.equals(""))
				players = vp.getName();
			else
				players = players + ", " + vp.getName();
		}

		getDiscordBot().reply(players, "Participating Players (" + VaroPlayer.getVaroPlayers().size() + ")", Color.BLUE, event);
	}
}