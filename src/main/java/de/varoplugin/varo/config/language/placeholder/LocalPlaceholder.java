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

package de.varoplugin.varo.config.language.placeholder;

import org.bukkit.entity.Player;

import de.varoplugin.varo.config.language.component.MessageComponent;

public class LocalPlaceholder implements MessageComponent {

	private final String name;
	private final int id;

	public LocalPlaceholder(String name, int id) {
		this.name = name;
		this.id = id;
	}

	@Override
	public String value() {
		return "%" + this.name + "%";
	}

	@Override
	public String value(Object... localPlaceholders) {
		return localPlaceholders[this.id].toString();
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return localPlaceholders[this.id].toString();
	}

	@Override
	public boolean shouldEscape() {
		return true;
	}
}
