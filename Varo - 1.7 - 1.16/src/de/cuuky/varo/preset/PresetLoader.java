package de.cuuky.varo.preset;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

public class PresetLoader {

	private final File file;
	private final File configDir;

	public PresetLoader(String name) {
		this.file = new File("plugins/Varo/presets/" + name);
		this.configDir = new File("plugins/Varo/config/");
	}

	private void copy(File from, File to) {
		if (!to.exists())
			to.mkdirs();

		for (File config : from.listFiles()) {
			if (!config.isFile())
				continue;

			try {
				File toFile = new File(to.getPath() + File.separator + config.getName());
				Files.copy(config, toFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void copyCurrentSettingsTo() {
		this.copy(this.configDir, file);
	}

	public void loadSettings() {
		this.copy(file, this.configDir);
	}

	public File getFile() {
		return file;
	}
}