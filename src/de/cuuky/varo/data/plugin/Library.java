package de.cuuky.varo.data.plugin;

import java.io.File;
import java.util.Arrays;
import java.util.function.Supplier;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.spigot.FileDownloader;

public abstract class Library {

	protected static final File LIB_FOLDER = new File("plugins/Varo/libs");

	private final String name;
	private final String className;
	private final String link;
	private final Supplier<Boolean> shouldLoad;

	public Library(String name, String className, String link, Supplier<Boolean> shouldLoad) {
		this.name = name;
		this.className = className;
		this.link = link;
		this.shouldLoad = shouldLoad;
	}

	public Library(String name, String className, String link, ConfigSetting... configSettings) {
		this(name, className, link, () -> Arrays.stream(configSettings).anyMatch(ConfigSetting::getValueAsBoolean));
	}

	public Library(String name, String className, String link) {
		this(name, className, link, () -> true);
	}

	public boolean shouldLoad() {
		try {
			Class.forName(this.className);
			// already loaded
			return false;
		} catch (ClassNotFoundException e) {
			return this.shouldLoad.get();
		}
	}

	public void load() {
		if (!LIB_FOLDER.exists())
			LIB_FOLDER.mkdirs();

		File jar = new File(LIB_FOLDER, this.getName() + ".jar");

		// download jar if necessary
		if (!jar.exists() || jar.length() == 0) {
			System.out.println(Main.getConsolePrefix() + "Downloading " + this.link);
			try {
				jar.createNewFile();

				FileDownloader fileDownloader = new FileDownloader(this.link, jar.getAbsolutePath());
				fileDownloader.startDownload();
			} catch (Throwable t) {
				System.out.println(Main.getConsolePrefix() + "Failed to download plugin: " + this.getName());
				t.printStackTrace();
			}
		}

		this.init(jar);
	}

	protected abstract void init(File jar);

	public String getName() {
		return this.name;
	}
}
