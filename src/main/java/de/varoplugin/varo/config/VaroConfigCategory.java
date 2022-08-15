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

import java.util.Objects;

import de.varoplugin.varo.VaroJavaPlugin;
import de.varoplugin.varo.api.config.ConfigCategory;

public class VaroConfigCategory implements ConfigCategory {
	
	private static final String FILE_EXTENSION = ".yml";
	
	public static final ConfigCategory MAIN = new VaroConfigCategory("Main", "The main config settings of this plugin"),
			START = new VaroConfigCategory("Start", "Everytging start related"),
			BOTS = new VaroConfigCategory("Bot", "Everything regarding the Discord and Telegram bots. Need help? " + VaroJavaPlugin.DISCORD_INVITE),
			SCOREBOARD = new VaroConfigCategory("Scoreboard", "Everything scoreboard related");
	
	private final String name;
	private final String description;
	private final String fileName;

	// TODO Add GUI icon
	public VaroConfigCategory(String name, String description) {
		if(name == null || description == null)
			throw new IllegalArgumentException();
		
		this.name = name;
		this.description = description;
		this.fileName = name.toLowerCase() + FILE_EXTENSION;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String getFileName() {
		return this.fileName;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		return Objects.equals(this.name, ((VaroConfigCategory) obj).name);
	}
}
