package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.config.config.ConfigEntry;

public class RegisterCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public RegisterCommand() {
		super("register", new String[] { "verify", "link" }, "Registriert dich per Code mit dem Discordbot");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if(event.getAuthor().isBot() || event.getAuthor().equals(super.getDiscordBot().getJda().getSelfUser()))
			return;

		if(super.getDiscordBot().getRegisterChannel() == null)
			return;

		if(super.getDiscordBot().getRegisterChannel().getIdLong() != event.getTextChannel().getIdLong()) {
			getDiscordBot().sendMessage("Bitte nutze den " + super.getDiscordBot().getRegisterChannel().getAsMention() + " Channel zum verifizieren, " + event.getAuthor().getAsMention() + "!", "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		TextChannel channel = event.getTextChannel();

		if(args.length == 0) {
			getDiscordBot().sendMessage("Usage: '" + ConfigEntry.DISCORDBOT_COMMANDTRIGGER.getValueAsString() + "verify <Code>' " + event.getAuthor().getAsMention(), "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		int code;
		try {
			code = Integer.valueOf(args[0]);
		} catch(Exception e) {
			getDiscordBot().sendMessage("Usage: '" + ConfigEntry.DISCORDBOT_COMMANDTRIGGER.getValueAsString() + "verify <Code>' " + event.getAuthor().getAsMention(), "ERROR", Color.RED, event.getTextChannel());
			return;
		}

		for(BotRegister reg : BotRegister.getBotRegister())
			if(reg.isActive() && reg.getUserId() == event.getAuthor().getIdLong()) {
				channel.sendMessage("Discord Account already used! " + event.getAuthor().getAsMention()).queue();
				return;
			}

		for(BotRegister reg : BotRegister.getBotRegister()) {
			if(reg.getCode() == code && !reg.isActive()) {
				reg.setUserId(Long.valueOf(event.getAuthor().getId()));
				getDiscordBot().sendMessage("Discord Account '" + event.getAuthor().getAsMention() + "' successfully linked with the MC-Account '" + reg.getPlayerName() + "'!", "SUCCESS", Color.GREEN, event.getTextChannel());
				reg.setCode(reg.generateCode());
				return;
			}
		}

		getDiscordBot().sendMessage("Code not found!", "ERROR", Color.RED, event.getTextChannel());
	}
}
