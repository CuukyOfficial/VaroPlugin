package de.cuuky.varo.bot.discord.listener;

import java.awt.Color;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.configuration.config.ConfigEntry;

public class DiscordBotEventListener implements EventListener {

	/*
	 * OLD CODE
	 */

	private MessageReceivedEvent lastEvent;

	public boolean isAliases(String command, String[] aliases) {
		for(String s : aliases)
			if(command.toLowerCase().equals(s.toLowerCase()))
				return true;

		return false;
	}

	@Override
	public void onEvent(Event event) {
		if(!(event instanceof MessageReceivedEvent))
			return;

		MessageReceivedEvent messageEvent = (MessageReceivedEvent) event;
		try {
			if(messageEvent.getAuthor().equals(BotLauncher.getDiscordBot().getJda().getSelfUser()))
				return;
		} catch(NullPointerException e) {
			return;
		}

		if(this.lastEvent != null)
			if(lastEvent.getMessageId().equals(messageEvent.getMessageId()))
				return;

		this.lastEvent = messageEvent;
		String message = messageEvent.getMessage().getContentDisplay();
		if(!message.toLowerCase().startsWith(ConfigEntry.DISCORDBOT_COMMANDTRIGGER.getValueAsString().toLowerCase().replace(" ", "")))
			return;

		if(message.replace(" ", "").equalsIgnoreCase(ConfigEntry.DISCORDBOT_COMMANDTRIGGER.getValueAsString().replace(" ", ""))) {
			messageEvent.getTextChannel().sendMessage("Type '" + ConfigEntry.DISCORDBOT_COMMANDTRIGGER.getValueAsString() + "help' for help.").queue();
			return;
		}

		String replace = ConfigEntry.DISCORDBOT_COMMANDTRIGGER.getValueAsString();
		String command = message.toUpperCase().replaceFirst("(?i)" + replace.toUpperCase(), "").split(" ")[0];
		String[] args = message.toUpperCase().replaceFirst(replace.toUpperCase() + command.toUpperCase() + " ", "").split(" ");

		for(DiscordBotCommand cmd : DiscordBotCommand.getCommands()) {
			if(!cmd.getName().equalsIgnoreCase(command) && !isAliases(command, cmd.getAliases()))
				continue;

			cmd.onEnable(args, messageEvent);
			return;
		}

		BotLauncher.getDiscordBot().sendMessage("Command '" + command + "' not found!", "ERROR", Color.RED, messageEvent.getTextChannel());
	}
}