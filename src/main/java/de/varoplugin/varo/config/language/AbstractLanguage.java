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

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class AbstractLanguage implements Language {

	private final int id;
	private final String name;
	private final Translation<?>[] translations;

	public AbstractLanguage(int id, String name, Translation<?>[] translations) {
		this.id = id;
		this.name = name;
		this.translations = translations;
	}

	public void load(File file) throws IOException {
		YamlConfiguration yaml;
		if (file.exists())
			try (InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8)) {
				yaml = YamlConfiguration.loadConfiguration(inputStreamReader);
			}
		else {
			file.getParentFile().mkdirs();
			file.createNewFile();
			yaml = new YamlConfiguration();
		}

		boolean changed = false;
		for (Translation<?> translation : this.translations)
			if (!yaml.contains(translation.path())) {
				translation.saveValue(yaml);
				changed = true;
			} else
				translation.loadValue(yaml);

		if (changed)
			try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
				outputStreamWriter.write(yaml.saveToString());
			}
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Translation<?>[] getTranslations() {
		return this.translations;
	}
}
