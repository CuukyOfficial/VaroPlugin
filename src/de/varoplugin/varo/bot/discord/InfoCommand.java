package de.varoplugin.varo.bot.discord;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.bot.BotMessage;
import de.varoplugin.varo.bot.BotMessageComponent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class InfoCommand extends Command {
	
	private final String message;

	InfoCommand(VaroPlugin plugin) {
		super("info", "Shows information about this plugin");
		
		this.message = plugin.getName() + "\nDiscord: " + plugin.getDiscordInvite() + "\nDownload: " + plugin.getWebsite()
			+ "\nSource code: " + plugin.getGithub() + "\nLicense: GNU AGPL v3";
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(new BotMessage().setBody(new BotMessageComponent(message, true)), event);
	}
}
