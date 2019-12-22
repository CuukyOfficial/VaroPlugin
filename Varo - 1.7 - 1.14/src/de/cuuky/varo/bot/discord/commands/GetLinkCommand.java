package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.config.config.ConfigEntry;

public class GetLinkCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public GetLinkCommand() {
		super("getLink", new String[]{"getVerify"}, "Gibt den verlinkten MC-Account des Spielers zurück.");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if (args.length != 1 && event.getMessage().getMentionedUsers().size() == 0) {
			event.getTextChannel().sendMessage("varo getLink <User / MC-Name>").queue();
			return;
		}

		if (!ConfigEntry.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
			event.getChannel().sendMessage("Das Verifzierungs-System wurde in der Config deaktiviert!").queue();
			return;
		}

		BotRegister reg = null;
		if (event.getMessage().getMentionedUsers().size() != 0)
			BotRegister.getRegister(event.getMessage().getMentionedUsers().get(0));
		else
			try {
				reg = BotRegister.getRegister(super.getDiscordBot().getJda().getUsersByName(args[0], true).get(0));
			} catch (Exception e) {
			}

		if (reg == null) {
			event.getChannel().sendMessage(Main.getPrefix() + "Der Spieler " + args[0] + " hat den Server noch nie betreten!").queue();
			return;
		}

		User user = super.getDiscordBot().getJda().getUserById(reg.getUserId());
		if (user == null) {
			event.getChannel().sendMessage(Main.getPrefix() + "User für diesen Spieler nicht gefunden!").queue();
			return;
		}

		getDiscordBot().sendMessage("Der MC-Account von " + user.getAsMention() + " lautet " + reg.getPlayerName() + "!", "GET LINK", Color.BLUE, event.getTextChannel());
	}
}
