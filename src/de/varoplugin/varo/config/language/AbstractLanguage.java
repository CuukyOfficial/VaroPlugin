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
