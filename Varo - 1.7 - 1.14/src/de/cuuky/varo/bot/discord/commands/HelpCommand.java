package de.cuuky.varo.bot.discord.commands;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public HelpCommand() {
		super("help", new String[] { "commands" }, "Zeigt alle Befehle des DiscordBots an");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		String cmds = "";
		for(DiscordBotCommand cmd : DiscordBotCommand.getCommands()) {
			String aliases = "";
			for(String a : cmd.getAliases())
				if(aliases.equals(""))
					aliases = a;
				else
					aliases = aliases + ", " + a;
			cmds = cmds + "\n" + cmd.getName() + ":\n" + "  Aliases: " + aliases + "\n  Description: " + cmd.getDescription();
		}

		super.getDiscordBot().sendRawMessage("``` Hier eine Übersicht aller Commands: " + cmds + "```", event.getTextChannel());
	}

}
