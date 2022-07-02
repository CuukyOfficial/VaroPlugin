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

		this.message = String.format("**%s v%s**%nDiscord: %s%nDownload: %s%nSource-Code: %s%n License GNU AGPL v3",
				plugin.getName(), plugin.getDescription().getVersion(), plugin.getDiscordInvite(), plugin.getWebsite(), plugin.getGithub());
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(new BotMessage().setBody(new BotMessageComponent(message, false)), event);
	}
}
