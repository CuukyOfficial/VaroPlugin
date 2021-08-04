package de.cuuky.varo.data.plugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class UrlJarLoader implements JarLoader {

	private final Method addUrlMethod;

	public UrlJarLoader() throws NoSuchMethodException, SecurityException {
		this.addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		this.addUrlMethod.setAccessible(true);
	}

	@Override
	public void load(File jar) throws Exception {
		URLClassLoader classLoader = (URLClassLoader) this.getClass().getClassLoader();
		this.addUrlMethod.invoke(classLoader, jar.toURI().toURL());
	}

}
