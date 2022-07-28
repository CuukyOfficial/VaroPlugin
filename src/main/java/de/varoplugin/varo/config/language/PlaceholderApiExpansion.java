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

package de.varoplugin.varo.config.language;

import java.util.Map;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.placeholder.GlobalPlaceholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderApiExpansion extends PlaceholderExpansion {

	private final String identifier;
	private final String author;
	private final String version;
	private final Map<String, GlobalPlaceholder> placeholders;

	public PlaceholderApiExpansion(String identifier, String author, String version, Map<String, GlobalPlaceholder> placeholders) {
		this.identifier = identifier;
		this.author = author;
		this.version = version;
		this.placeholders = placeholders;
	}

	@Override
	public String getIdentifier() {
		return this.identifier;
	}

	@Override
	public String getAuthor() {
		return this.author;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {
		GlobalPlaceholder globalPlaceholder = this.placeholders.get(params);
		if (globalPlaceholder == null)
			return null;
		if (player == null)
			return globalPlaceholder.value();
		return globalPlaceholder.value(player);
	}
}
