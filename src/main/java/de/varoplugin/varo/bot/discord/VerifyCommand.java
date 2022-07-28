/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

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
