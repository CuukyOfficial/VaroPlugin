package de.varoplugin.varo.dependencies;

import java.net.URLClassLoader;
import java.util.logging.Level;

import de.varoplugin.varo.VaroPlugin;

class JarDependency extends Dependency {

	private static JarLoader jarLoader;

	JarDependency(String name, String link, String hash) {
		super(name, link, hash);
	}

	JarDependency(String name, String link, String hash, LoadPolicy loadPolicy) {
		super(name, link, hash, loadPolicy);
	}

	@Override
	protected void init(VaroPlugin varo) {
		try {
			if (jarLoader == null) {
				if (System.getProperty("java.version").startsWith("1.")) {
					// java 8 or lower
					if (this.getClass().getClassLoader() instanceof URLClassLoader) {
						jarLoader = new UrlJarLoader();
					} else {
						varo.getLogger().severe("Failed to load library! Unknown ClassLoader");
						return;
					}
				} else
					// java 9+
					jarLoader = new BukkitJarLoader();
			}

			jarLoader.load(this.getFile());
		} catch (Exception e) {
			varo.getLogger().log(Level.SEVERE, "Failed to load library!", e);
		}
	}
}
