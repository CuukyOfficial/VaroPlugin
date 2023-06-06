package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import org.bukkit.GameMode;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.entity.player.VaroPlayer;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class OnlineCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public OnlineCommand() {
		super("online", "Zeigt alle Spieler an, die online sind");
	}

	@Override
	public void onExecute(SlashCommandInteraction event) {
	    String players = "";
		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
			if (vp.getPlayer().getGameMode() != GameMode.SURVIVAL)
				continue;

			if (players.equals(""))
				players = vp.getName();
			else
				players = players + ", " + vp.getName();
		}

		getDiscordBot().reply(players, "Online (" + VaroPlayer.getOnlinePlayer().size() + ")", Color.BLUE, event);
	}
}