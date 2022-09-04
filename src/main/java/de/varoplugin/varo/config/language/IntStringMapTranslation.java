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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

public class IntStringMapTranslation extends GenericTranslation<Map<Integer, String>> {

	public IntStringMapTranslation(String path, Map<Integer, String> value) {
		super(path, value);
	}

	@Override
	public void loadValue(YamlConfiguration yaml) {
		Map<Integer, String> map = new HashMap<>();
		yaml.getConfigurationSection(this.path()).getValues(false).entrySet().forEach(entry -> map.put(Integer.parseInt(entry.getKey()), entry.getValue().toString()));
		this.value = Collections.unmodifiableMap(map);
	}

	@Override
	public void saveValue(YamlConfiguration yaml) {
		yaml.createSection(this.path(), this.value);
	}

}
