package de.cuuky.varo.bot.discord.commands;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public HelpCommand() {
		super("help", new String[] { "commands" }, "Zeigt alle Befehle des DiscordBots an");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		StringBuilder cmds = new StringBuilder();
		for (DiscordBotCommand cmd : DiscordBotCommand.getCommands()) {
			StringBuilder aliases = new StringBuilder();
			for (String a : cmd.getAliases())
				if (aliases.toString().equals(""))
					aliases = new StringBuilder(a);
				else
					aliases.append(", ").append(a);
			cmds.append("\n").append(cmd.getName()).append(":\n").append("  Aliases: ").append(aliases).append("\n  Description: ").append(cmd.getDescription());
		}

		super.getDiscordBot().sendRawMessage("``` Hier eine Übersicht aller Commands: " + cmds + "```", event.getTextChannel());
	}

}