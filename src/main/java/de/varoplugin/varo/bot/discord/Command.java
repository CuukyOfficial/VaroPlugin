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

import de.varoplugin.varo.api.config.ConfigEntry;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
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
		return Commands.slash(this.name, this.desc).addOptions(this.options).setDefaultPermissions(DefaultMemberPermissions.ENABLED);
	}

	public String getName() {
		return this.name;
	}
}
