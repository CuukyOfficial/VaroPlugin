package de.cuuky.varo.bot.discord;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.bot.BotMessage;
import de.cuuky.varo.bot.BotMessageComponent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class InfoCommand extends Command {
	
	private static final String MESSAGE = Main.getPluginName() + "\nDiscord: " + Main.DISCORD_INVITE + "\nDownload: " + Main.WEBSITE + "\nSource code: " + Main.GITHUB + "\nLicense: GNU AGPL v3";

	InfoCommand() {
		super("info", "Shows information about this plugin");
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(new BotMessage().setBody(new BotMessageComponent(MESSAGE, true)), event);
	}
}
