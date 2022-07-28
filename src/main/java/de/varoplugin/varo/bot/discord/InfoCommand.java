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

import de.varoplugin.varo.VaroPlugin;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

class InfoCommand extends Command {
	
	private final String message;

	InfoCommand(VaroPlugin plugin) {
		super("info", "Shows information about this plugin");

		this.message = String.format("**%s v%s**%nDiscord: %s%nDownload: %s%nSource-Code: %s%n License GNU AGPL v3",
				plugin.getName(), plugin.getDescription().getVersion(), plugin.getDiscordInvite(), plugin.getWebsite(), plugin.getGithub());
	}

	@Override
	void exec(DiscordBot bot, Member member, SlashCommandInteractionEvent event) {
		bot.reply(event, this.message, "");
	}
}
