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
