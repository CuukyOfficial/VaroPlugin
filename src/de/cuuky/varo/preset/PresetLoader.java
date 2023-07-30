package de.cuuky.varo.preset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.common.io.Files;

public class PresetLoader {

	private static final Path PRESET_PATH = Paths.get("plugins/Varo/presets/");

	private final File file;
	private final File configDir;

	public PresetLoader(String name) {
		this.file = new File("plugins/Varo/presets/" + name);
		this.configDir = new File("plugins/Varo/config/");
	}

	private boolean copy(File from, File to) {
		if (!Paths.get(file.getPath()).normalize().startsWith(PRESET_PATH))
			return false;

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
				return false;
			}
		}
		return true;
	}

	public boolean copyCurrentSettingsTo() {
		return this.copy(this.configDir, file);
	}

	public boolean loadSettings() {
		return this.copy(file, this.configDir);
	}

	public File getFile() {
		return file;
	}
}