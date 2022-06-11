package de.varoplugin.varo.bot.discord;

import de.varoplugin.varo.api.config.ConfigEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

abstract class Command {

	private final String name;
	private final String desc;
	private final OptionData[] options;

	Command(String name, String desc, OptionData... options) {
		this.name = name;
		this.desc = desc;
		this.options = options;
	}

	Command(ConfigEntry<String> name, ConfigEntry<String> desc, OptionData... options) {
		this(name.getValue(), desc.getValue(), options);
	}

	abstract void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event);

	CommandData buildCommandData() {
		return Commands.slash(this.name, this.desc).addOptions(this.options).setDefaultEnabled(true);
	}
}
