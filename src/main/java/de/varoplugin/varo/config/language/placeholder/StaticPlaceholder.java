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

public class StaticPlaceholder extends GenericGlobalPlaceholder {
	
	private final String value;
	
	public StaticPlaceholder(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public String value(Player player, Object... localPlaceholders) {
		return this.value();
	}
}
