package de.varoplugin.varo.bot.discord;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
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

	Command(ConfigSetting name, ConfigSetting desc, OptionData... options) {
		this(name.getValueAsString().toLowerCase(), desc.getValueAsString(), options);
	}

	abstract void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event);

	CommandData buildCommandData() {
		return Commands.slash(this.name, this.desc).addOptions(this.options).setDefaultEnabled(true);
	}
}
