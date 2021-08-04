package de.cuuky.varo.data.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class JarLibrary extends Library {

	private static final File LIB_FOLDER = new File("plugins/Varo/libs");
	private static JarLoader jarLoader;

	private String link;

	public JarLibrary(String name, String className, String link, ConfigSetting... configSettings) {
		super(name, className, configSettings);
		this.link = link;
	}

	@Override
	public void load() {
		if (!LIB_FOLDER.exists())
			LIB_FOLDER.mkdirs();

		File jar = new File(LIB_FOLDER, this.getName() + ".jar");

		// download jar if necessary
		if (!jar.exists()) {
			System.out.println(Main.getConsolePrefix() + "Downloading " + this.link);
			try {
				jar.createNewFile();

				ReadableByteChannel rbc = Channels.newChannel(new URL(this.link).openStream());

				try (FileOutputStream fos = new FileOutputStream(jar); FileChannel fs = fos.getChannel()) {
					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
				}
			} catch (Throwable t) {
				System.out.println(Main.getConsolePrefix() + "Failed to download plugin: " + this.getName());
				t.printStackTrace();
			}
		}

		// load jar
		try {
			if (jarLoader == null) {
				if (System.getProperty("java.version").startsWith("1.")) {
					// java 8 or lower
					if (this.getClass().getClassLoader() instanceof URLClassLoader) {
						jarLoader = new UrlJarLoader();
					} else {
						System.out.println(Main.getConsolePrefix() + "Failed to load library! Unknown ClassLoader");
						Main.getInstance().fail();
						return;
					}
				} else
					// java 9+
					jarLoader = new BukkitJarLoader();
			}

			jarLoader.load(jar);
		} catch (Exception e) {
			System.out.println(Main.getConsolePrefix() + "Failed to load library!");
			e.printStackTrace();
			Main.getInstance().fail();
			return;
		}
	}
}
