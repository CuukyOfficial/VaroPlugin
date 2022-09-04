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

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

public class StringArray2Translation extends GenericTranslation<String[][]> {

	public StringArray2Translation(String path, String[]... value) {
		super(path, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadValue(YamlConfiguration yaml) {
		this.value = ((List<List<String>>) yaml.getConfigurationSection(this.path()).getList(this.path())).stream()
				.map(list -> list.toArray(new String[list.size()])).toArray(String[][]::new);
	}

	@Override
	public void saveValue(YamlConfiguration yaml) {
		yaml.set(this.path(), Arrays.asList(this.value));
	}

}
