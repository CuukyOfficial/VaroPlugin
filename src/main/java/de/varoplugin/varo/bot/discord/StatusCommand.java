package de.varoplugin.varo.bot.discord;

import org.bukkit.Bukkit;

import de.varoplugin.varo.config.VaroConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class StatusCommand extends Command {

	StatusCommand(VaroConfig config) {
		super(config.bot_discord_command_status_name, config.bot_discord_command_status_desc);
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(event, bot.messages().bot_discord_command_status, Bukkit.getServer().hasWhitelist(), "TODO", Bukkit.getOnlinePlayers().size());
	}
}
