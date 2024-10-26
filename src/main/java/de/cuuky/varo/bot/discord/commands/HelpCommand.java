package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class HelpCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public HelpCommand() {
		super("help", "Zeigt alle Befehle des DiscordBots an");
	}

	@Override
	public void onExecute(SlashCommandInteraction event) {
		StringBuilder cmds = new StringBuilder();
		cmds.append("```Hier eine Übersicht aller Commands: ");
		for (DiscordBotCommand cmd : DiscordBotCommand.getCommands())
			cmds.append("\n\n").append(cmd.getName()).append(":\n  Description: ").append(cmd.getDescription());
		cmds.append("```");
		getDiscordBot().reply(cmds.toString(), "Help", Color.BLUE, event);
	}
}