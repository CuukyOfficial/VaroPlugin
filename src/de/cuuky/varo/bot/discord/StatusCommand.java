package de.cuuky.varo.bot.discord;

import org.bukkit.Bukkit;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.bot.BotMessage;
import de.cuuky.varo.bot.BotMessageComponent;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class StatusCommand extends Command {
	
	private final BotMessageComponent[] messageComponents = BotMessageComponent.splitPlaceholders(ConfigMessages.BOT_DISCORD_COMMAND_STATUS.getValue(), false, true, "%whitelist%", "%gamestate%", "%online%");

	StatusCommand() {
		super(ConfigSetting.DISCORDBOT_COMMAND_STATUS_NAME, ConfigSetting.DISCORDBOT_COMMAND_STATUS_DESC);
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(new BotMessage().setBody(BotMessageComponent.replacePlaceholders(this.messageComponents, new String[] { "%whitelist%", "%gamestate%", "%online%"},
				new String[] { String.valueOf(Bukkit.getServer().hasWhitelist()), Main.getVaroGame().getGameState().toString(),
						String.valueOf(Bukkit.getOnlinePlayers().size())})), event);
	}
}
