package de.varoplugin.varo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

import de.varoplugin.cfw.dependencies.Dependency;
import de.varoplugin.cfw.dependencies.JarDependency;
import de.varoplugin.cfw.dependencies.NoInitDependency;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.config.VaroConfig;

public class Dependencies {

	protected static final String LIB_FOLDER = "plugins/VaroPlugin/libs";

	private static final String MAVEN_CENTERAL = "https://repo1.maven.org/maven2/";
	private static final String SONATYPE_OSS = "https://s01.oss.sonatype.org/content/repositories/snapshots/";
	private static final String VAROPLUGIN_NEXUS = "https://repo.varoplugin.de/repository/maven-public/";

	private static final URL DEPENDENCY_FILE = Dependencies.class.getClassLoader().getResource("dependencies.txt");

	//public final static Dependency GUAVA = new VaroDependency("guava", new ClassPolicy("com.google.common.hash.Hashing"));

	public final static VaroDependency SIMPLE_YAML = new VaroDependency("Simple-Yaml", VAROPLUGIN_NEXUS, NoInitDependency::new);
	public final static VaroDependency SNAKE_YAML = new VaroDependency("snakeyaml", MAVEN_CENTERAL, NoInitDependency::new);
	public static final VaroDependency ORMLITE_JDBC = new VaroDependency("ormlite-jdbc", MAVEN_CENTERAL, JarDependency::new);

	private static final Collection<VaroDependency> OPTIONAL_DEPENDENCIES = new ArrayList<>();

	static {
		OPTIONAL_DEPENDENCIES.add(new VaroDependency("JDA", MAVEN_CENTERAL, JarDependency::new, new ConfigEntryPolicy("net.dv8tion.jda.api.JDA", config -> config.bot_discord_enabled)));
		OPTIONAL_DEPENDENCIES.add(new VaroDependency("h2", MAVEN_CENTERAL, JarDependency::new, config -> config.db_type.getValue().equals("h2")));
		OPTIONAL_DEPENDENCIES.add(new VaroDependency("adventure-text-minimessage", SONATYPE_OSS, JarDependency::new, new ConfigEntryPolicy("net.kyori.adventure.text.minimessage.MiniMessage", config -> config.minimessage)));
		OPTIONAL_DEPENDENCIES.add(new VaroDependency("adventure-platform-bukkit", SONATYPE_OSS, JarDependency::new, new ConfigEntryPolicy("me.clip.placeholderapi.libs.kyori.adventure.platform.bukkit.BukkitAudiences", config -> config.minimessage)));
	}

	public static void loadNeeded(VaroPlugin varo) {
		for (VaroDependency lib : OPTIONAL_DEPENDENCIES)
			try {
				lib.load(varo);
			} catch (Throwable e) {
				varo.getLogger().log(Level.SEVERE, "Unable to load dependency", e);
			}
	}

	public static class VaroDependency {

		private final Dependency[] dependencies;
		private final LoadPolicy loadPolicy;

		private VaroDependency(String name, String repo, DependencySupplier supplier, LoadPolicy loadPolicy) {
			this.loadPolicy = loadPolicy;
			// TODO this should be cleaned up and optimized
			List<Dependency> dependencies = new ArrayList<>();
			try (Scanner scanner = new Scanner(DEPENDENCY_FILE.openStream())) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] split = line.split(":");
					if (split.length != 5)
						throw new Error();
					if (split[0].equals(name))
						dependencies.add(supplier.get(split[2] + "-" + split[3], LIB_FOLDER, repo + split[1].replace('.', '/') + "/" + split[2] + "/" + split[3] + "/" + split[2] + "-" + split[3] + ".jar", split[4]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (dependencies.isEmpty())
				throw new Error("Missing dependency information for " + name);
			this.dependencies = dependencies.toArray(new Dependency[dependencies.size()]);
		}

		private VaroDependency(String name, String repo, DependencySupplier supplier) {
			this(name, repo, supplier, config -> true);
		}

		public void load(Plugin plugin) throws Throwable {
			if (this.loadPolicy.shouldLoad(((VaroPlugin) plugin).getVaroConfig())) {
				for (Dependency dependency : this.dependencies) {
					plugin.getLogger().info("Loading " + dependency.getName());
					dependency.load(plugin);
				}
			}
		}

		public URL[] getUrls() throws MalformedURLException {
			URL[] urls = new URL[this.dependencies.length];
			for (int i = 0; i < urls.length; i++)
				urls[i] = this.dependencies[i].getUrl();
			return urls;
		}
	}

	@FunctionalInterface
	private interface DependencySupplier {
		Dependency get(String name, String folder, String link, String hash);
	}

	private interface LoadPolicy {
		boolean shouldLoad(VaroConfig config);
	}

	private static class ClassPolicy implements LoadPolicy {

		private final String className;

		public ClassPolicy(String className) {
			this.className = className;
		}

		@Override
		public boolean shouldLoad(VaroConfig config) {
			try {
				Class.forName(this.className);
				// already loaded
				return false;
			} catch (ClassNotFoundException e) {
				return true;
			}
		}
	}

	private static class ConfigEntriesPolicy extends ClassPolicy {

		private final Function<VaroConfig, ConfigEntry<Boolean>[]> configEntryGetter;

		private ConfigEntriesPolicy(String className, Function<VaroConfig, ConfigEntry<Boolean>[]> configEntriesGetter) {
			super(className);
			this.configEntryGetter = configEntriesGetter;
		}

		@Override
		public boolean shouldLoad(VaroConfig config) {
			return super.shouldLoad(config) && Arrays.stream(this.configEntryGetter.apply(config)).allMatch(ConfigEntry::getValue);
		}
	}

	private static class ConfigEntryPolicy extends ConfigEntriesPolicy {

		@SuppressWarnings("unchecked")
		private ConfigEntryPolicy(String className, Function<VaroConfig, ConfigEntry<Boolean>> configEntryGetter) {
			super(className, config -> new ConfigEntry[] {configEntryGetter.apply(config)});
		}
	}

	@FunctionalInterface
	private interface ConfigEntryGetter {
		ConfigEntry<Boolean>[] getConfigEntries(VaroConfig config);
	}
}
