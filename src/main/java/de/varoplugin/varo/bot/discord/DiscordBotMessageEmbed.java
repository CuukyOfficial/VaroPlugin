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

import de.varoplugin.varo.config.language.translatable.TranslatableMessageComponent;

public abstract class DiscordBotMessageEmbed {

	private final TranslatableMessageComponent title, body;
	
	public DiscordBotMessageEmbed(String path, String... localPlaceholderNames) {
		this.title = this.createMessageComponent(path + ".title", localPlaceholderNames);
		this.body = this.createMessageComponent(path + ".body", localPlaceholderNames);
	}
	
	protected abstract TranslatableMessageComponent createMessageComponent(String path, String... localPlaceholderNames);

	public TranslatableMessageComponent title() {
		return this.title;
	}
	
	public TranslatableMessageComponent body() {
		return this.body;
	}
}
