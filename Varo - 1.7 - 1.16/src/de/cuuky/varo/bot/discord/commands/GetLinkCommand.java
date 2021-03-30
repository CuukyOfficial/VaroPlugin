package de.cuuky.varo.bot.discord.commands;

import java.awt.Color;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.discord.DiscordBotCommand;
import de.cuuky.varo.bot.discord.register.BotRegister;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetLinkCommand extends DiscordBotCommand {

	/*
	 * OLD CODE
	 */

	public GetLinkCommand() {
		super("getLink", new String[] { "getVerify" }, "Gibt den verlinkten MC-Account des Spielers zurueck.");
	}

	@Override
	public void onEnable(String[] args, MessageReceivedEvent event) {
		if (args.length != 1 && event.getMessage().getMentionedUsers().size() == 0) {
			event.getTextChannel().sendMessage("varo getLink <User / MC-Name>").queue();
			return;
		}

		if (!ConfigSetting.DISCORDBOT_VERIFYSYSTEM.getValueAsBoolean()) {
			event.getChannel().sendMessage("Das Verifzierungs-System wurde in der Config deaktiviert!").queue();
			return;
		}

		BotRegister reg = null;
		if (event.getMessage().getMentionedUsers().size() != 0)
			BotRegister.getRegister(event.getMessage().getMentionedUsers().get(0));
		else
			try {
				reg = BotRegister.getRegister(super.getDiscordBot().getJda().getUsersByName(args[0], true).get(0));
			} catch (Exception e) {}

		if (reg == null) {
			event.getChannel().sendMessage(Main.getPrefix() + "Der Spieler " + args[0] + " hat den Server noch nie betreten!").queue();
			return;
		}

		User user = super.getDiscordBot().getJda().getUserById(reg.getUserId());
		if (user == null) {
			event.getChannel().sendMessage(Main.getPrefix() + "User fuer diesen Spieler nicht gefunden!").queue();
			return;
		}

		getDiscordBot().sendMessage("Der MC-Account von " + user.getAsMention() + " lautet " + reg.getPlayerName() + "!", "GET LINK", Color.BLUE, event.getTextChannel());
	}
}