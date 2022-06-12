package de.varoplugin.varo.bot.discord;

import org.bukkit.Bukkit;

import de.varoplugin.varo.bot.BotMessage;
import de.varoplugin.varo.bot.BotMessageComponent;
import de.varoplugin.varo.config.VaroConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class StatusCommand extends Command {
	
	private final BotMessageComponent[] messageComponents;

	StatusCommand(VaroConfig config) {
		super(config.bot_discord_command_status_name, config.bot_discord_command_status_desc);

		this.messageComponents = BotMessageComponent.splitPlaceholders(config.bot_discord_command_status_message.getValue(), false, true, "%whitelist%", "%gamestate%", "%online%");
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		BotMessageComponent[] replaced = BotMessageComponent.replacePlaceholders(this.messageComponents, new String[] { "%whitelist%", "%gamestate%", "%online%"},
				new String[] { String.valueOf(Bukkit.getServer().hasWhitelist()), "TODO", String.valueOf(Bukkit.getOnlinePlayers().size())});
		bot.reply(new BotMessage().setBody(replaced), event);
	}
}
