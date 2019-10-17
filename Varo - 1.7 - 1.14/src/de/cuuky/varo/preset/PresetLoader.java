package de.cuuky.varo.preset;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class PresetLoader {

	private File file;

	public PresetLoader(String name) {
		this.file = new File("plugins/Varo/presets/" + name);
	}

	public void copyCurrentSettingsTo() {
		if (!file.exists())
			file.mkdirs();

		for (File config : new File("plugins/Varo/").listFiles()) {
			if (!config.isFile())
				continue;

			try {
				Files.copy(config, new File(file.getPath() + "/" + config.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadSettings() {
		if (!file.exists())
			file.mkdirs();

		for (File config : file.listFiles()) {
			if (!config.isFile())
				continue;

			try {
				Files.copy(config, new File("plugins/Varo/" + config.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public File getFile() {
		return file;
	}
}
