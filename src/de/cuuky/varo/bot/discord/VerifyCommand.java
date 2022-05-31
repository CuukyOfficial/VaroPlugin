package de.cuuky.varo.bot.discord;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

class VerifyCommand extends Command {
	
	private static final String VERIFY_MODAL_ID = "varo::verify";
	private static final String VERIFY_MODAL_INPUT_ID = "varo::verify::code";
	private final Modal modal = Modal.create(VERIFY_MODAL_ID, ConfigMessages.BOT_DISCORD_MODAL_VERIFY_TITLE.getValue()).addActionRow(
			TextInput.create(VERIFY_MODAL_INPUT_ID, ConfigMessages.BOT_DISCORD_MODAL_VERIFY_INPUT_LABEL.getValue(), TextInputStyle.SHORT).build()).build();
	
	VerifyCommand() {
		super(ConfigSetting.DISCORDBOT_COMMAND_VERIFY_NAME, ConfigSetting.DISCORDBOT_COMMAND_VERIFY_DESC);
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		event.replyModal(this.modal).queue();
	}
}
