package de.cuuky.varo.bot.discord;

import java.awt.Color;

import de.cuuky.varo.app.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class InfoCommand extends Command {

	InfoCommand(String name) {
		super(name, "Shows information about this plugin");
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply("Discord: " + Main.DISCORD_INVITE, Main.getPluginName(), Color.BLUE, event);
	}
}
