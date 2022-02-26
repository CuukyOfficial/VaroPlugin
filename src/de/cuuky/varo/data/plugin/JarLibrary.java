package de.cuuky.varo.data.plugin;

import java.io.File;
import java.net.URLClassLoader;
import java.util.function.Supplier;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class JarLibrary extends Library {

	private static JarLoader jarLoader;

	public JarLibrary(String name, String className, String link, Supplier<Boolean> shouldLoad) {
		super(name, className, link, shouldLoad);
	}

	public JarLibrary(String name, String className, String link, ConfigSetting... configSettings) {
		super(name, className, link, configSettings);
	}

	public JarLibrary(String name, String className, String link) {
		super(name, className, link);
	}

	@Override
	protected void init(File jar) {
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
