package de.varoplugin.varo.bot.discord;

import org.bukkit.Bukkit;

import de.varoplugin.varo.bot.BotMessage;
import de.varoplugin.varo.bot.BotMessageComponent;
import de.varoplugin.varo.config.VaroConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class StatusCommand extends Command {
	
	private final BotMessageComponent[] messageComponents = BotMessageComponent.splitPlaceholders("//TODO", false, true, "%whitelist%", "%gamestate%", "%online%");

	StatusCommand(VaroConfig config) {
		super(config.bot_discord_command_status_name, config.bot_discord_command_status_desc);
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(new BotMessage().setBody(BotMessageComponent.replacePlaceholders(this.messageComponents, new String[] { "%whitelist%", "%gamestate%", "%online%"},
				new String[] { String.valueOf(Bukkit.getServer().hasWhitelist()), "TODO",
						String.valueOf(Bukkit.getOnlinePlayers().size())})), event);
	}
}
