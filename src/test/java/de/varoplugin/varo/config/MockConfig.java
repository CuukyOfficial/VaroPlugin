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

package de.varoplugin.varo.config;

import static de.varoplugin.varo.config.VaroConfigCategory.*;

import java.util.Arrays;
import java.util.List;

import de.varoplugin.varo.api.config.ConfigEntry;

public class MockConfig extends ConfigImpl {
	
	public static final List<String> LIST = Arrays.asList("ABC", "DEF");
	
	public final ConfigEntry<Integer> int_entry = new ConfigEntryImpl<>(MAIN, "int", 0__0, "u good java???");
	public final ConfigEntry<Long> long_entry = new ConfigEntryImpl<>(MAIN, "long", 69L, "69");
	// public final ConfigEntry<DisappointingMinecraftUpdates> enum_entry = new ConfigEntryImpl<>(MAIN, "enum", DisappointingMinecraftUpdates.RELEASE_1_9, "Thx Microsoft");
	public final ConfigEntry<List<String>> list_entry = new ConfigEntryImpl<>(MAIN, "list", LIST, "404 comment not found");
	
	protected MockConfig(String path) {
		super(path);
		this.addConfigEntry(this.int_entry);
		this.addConfigEntry(this.list_entry);
		// this.addConfigEntry(this.enum_entry);
		this.addConfigEntry(this.list_entry);
	}
	
	public enum DisappointingMinecraftUpdates {
		
		RELEASE_1_9,
		RELEASE_1_10,
		RELEASE_1_12,
		RELEASE_1_15,
		RELEASE_1_17,
		RELEASE_1_19,
		EVERYTHING_ELSE_SINCE_MICROSOFT_BOUGHT_MOJANG;
	}
}
