package de.varoplugin.varo.dependencies;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

class UrlJarLoader implements JarLoader {

	private final Method addUrlMethod;

	UrlJarLoader() throws NoSuchMethodException, SecurityException {
		this.addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		this.addUrlMethod.setAccessible(true);
	}

	@Override
	public void load(File jar) throws Exception {
		URLClassLoader classLoader = (URLClassLoader) this.getClass().getClassLoader();
		this.addUrlMethod.invoke(classLoader, jar.toURI().toURL());
	}

}
