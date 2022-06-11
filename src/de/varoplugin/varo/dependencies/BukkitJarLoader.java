package de.varoplugin.varo.dependencies;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * This class uses a fuckton of undocumented stuff and will most likely stop
 * working at some point in the future. Have fun xd
 * 
 * @author Almighty-Satan
 */
public class BukkitJarLoader implements JarLoader {

	private final HackClassLoader hackClassLoader;

	public BukkitJarLoader() throws NoSuchFieldException, SecurityException, ClassNotFoundException,
			IllegalArgumentException, IllegalAccessException {
		Object pluginClassLoader = this.getClass().getClassLoader();
		Class<?> pluginClassLoaderClass = Class.forName("org.bukkit.plugin.java.PluginClassLoader");

		if (pluginClassLoader.getClass() != pluginClassLoaderClass)
			throw new RuntimeException("Unknown ClassLoader: " + pluginClassLoader.getClass());

		this.hackClassLoader = new HackClassLoader();

		Field classesField = pluginClassLoaderClass.getDeclaredField("classes");
		classesField.setAccessible(true);
		Map<?, ?> originalMap = (Map<?, ?>) classesField.get(pluginClassLoader);
		classesField.set(pluginClassLoader, new HackMap<>(originalMap, this.hackClassLoader));
	}

	@Override
	public void load(File jar) throws Exception {
		this.hackClassLoader.addURL(jar.toURI().toURL());
	}

	private static class HackClassLoader extends URLClassLoader {

		public HackClassLoader() {
			super(new URL[0]);
		}

		@Override
		protected void addURL(URL url) {
			super.addURL(url);
		}
	}

	private static class HackMap<K, V> implements Map<K, V> {

		private final Map<K, V> parent;
		private final HackClassLoader classLoader;

		private HackMap(Map<K, V> parent, HackClassLoader classLoader) {
			this.parent = parent;
			this.classLoader = classLoader;
		}

		@Override
		public int size() {
			return this.parent.size();
		}

		@Override
		public boolean isEmpty() {
			return this.parent.isEmpty();
		}

		@Override
		public boolean containsKey(Object key) {
			return this.parent.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return this.parent.containsValue(value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public V get(Object key) {
			V parentObject = this.parent.get(key);
			if (parentObject != null)
				return parentObject;
			else
				try {
					return (V) this.classLoader.loadClass((String) key);
				} catch (ClassNotFoundException e) {
					return null;
				}
		}

		@Override
		public V put(K key, V value) {
			return this.parent.put(key, value);
		}

		@Override
		public V remove(Object key) {
			return this.parent.remove(key);
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			this.parent.putAll(m);
		}

		@Override
		public void clear() {
			this.parent.clear();
		}

		@Override
		public Set<K> keySet() {
			return this.parent.keySet();
		}

		@Override
		public Collection<V> values() {
			return this.parent.values();
		}

		@Override
		public Set<Entry<K, V>> entrySet() {
			return this.parent.entrySet();
		}
	}
}
