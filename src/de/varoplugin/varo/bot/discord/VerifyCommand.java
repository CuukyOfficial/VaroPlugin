package de.varoplugin.varo.bot.discord;

import de.varoplugin.varo.config.VaroConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

class VerifyCommand extends Command {
	
	private static final String VERIFY_MODAL_ID = "varo::verify";
	private static final String VERIFY_MODAL_INPUT_ID = "varo::verify::code";
	private final Modal modal;;
	
	VerifyCommand(VaroConfig config) {
		super(config.bot_discord_command_verify_name.getValue(), config.bot_discord_command_verify_desc.getValue());
		
		this.modal = Modal.create(VERIFY_MODAL_ID, config.bot_discord_modal_verify_title.getValue()).addActionRow(
				TextInput.create(VERIFY_MODAL_INPUT_ID, config.bot_discord_modal_verify_input_label.getValue(), TextInputStyle.SHORT).build()).build();
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		event.replyModal(this.modal).queue();
	}
}
