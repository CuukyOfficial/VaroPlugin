package de.varoplugin.varo.bot.discord;

import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.config.language.Messages;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

class VerifyCommand extends Command {
	
	public static final String VERIFY_MODAL_ID = "varo::verify";
	public static final String VERIFY_MODAL_INPUT_ID = "varo::verify::code";
	private final Modal modal;

	VerifyCommand(VaroConfig config, Messages messages) {
		super(config.bot_discord_command_verify_name, config.bot_discord_command_verify_desc);
		
		this.modal = Modal.create(VERIFY_MODAL_ID, messages.bot_discord_modal_verify_title.value()).addActionRow(
				TextInput.create(VERIFY_MODAL_INPUT_ID, messages.bot_discord_modal_verify_inputlabel.value(), TextInputStyle.SHORT).build()).build();
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		event.replyModal(this.modal).queue();
	}
}
