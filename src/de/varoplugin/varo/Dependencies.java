package de.varoplugin.varo;

import de.varoplugin.cfw.dependencies.Dependency;
import de.varoplugin.cfw.dependencies.JarDependency;
import de.varoplugin.cfw.dependencies.NoInitDependency;
import de.varoplugin.varo.api.config.ConfigEntry;
import de.varoplugin.varo.config.VaroConfig;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Level;

public class Dependencies {

	protected static final String LIB_FOLDER = "plugins/Varo/libs";

	private static final String MAVEN_CENTERAL = "https://repo1.maven.org/maven2/";
	private static final String VAROPLUGIN_NEXUS = "https://repo.varoplugin.de/repository/maven-public/";

	public final static Dependency GUAVA = new VaroDependency(new JarDependency("Guava-19.0", LIB_FOLDER,
			MAVEN_CENTERAL + "com/google/guava/guava/19.0/guava-19.0.jar", ""), new ClassPolicy("com.google.common.hash.Hashing"));
	public final static Dependency SIMPLE_YAML = new VaroDependency(new NoInitDependency("SimpleYAML-@SimpleYaml_version@", LIB_FOLDER,
			VAROPLUGIN_NEXUS + "me/carleslc/Simple-YAML/Simple-Yaml/@SimpleYaml_version@/Simple-Yaml-@SimpleYaml_version@.jar", "@SimpleYaml_hash@"));
	public final static Dependency SNAKE_YAML = new VaroDependency(new NoInitDependency("SnakeYAML-@SnakeYaml_version@", LIB_FOLDER,
			MAVEN_CENTERAL + "org/yaml/snakeyaml/@SnakeYaml_version@/snakeyaml-@SnakeYaml_version@.jar", "@SnakeYaml_hash@"));

	private static final Collection<Dependency> OPTIONAL_DEPENDENCIES = new ArrayList<>();

	static {
		OPTIONAL_DEPENDENCIES.add(new VaroDependency(new JarDependency("JDA-@JDA_version@", LIB_FOLDER, VAROPLUGIN_NEXUS + "net/dv8tion/JDA/@JDA_version@/JDA-@JDA_version@.jar", "@JDA_hash@"),
				new ConfigEntryPolicy("net.dv8tion.jda.api.JDABuilder", config -> config.bot_discord_enabled)));
	}

	public static void loadNeeded(VaroPlugin varo) {
		for (Dependency lib : OPTIONAL_DEPENDENCIES)
			try {
				lib.load(varo);
			} catch (Throwable e) {
				varo.getLogger().log(Level.SEVERE, "Unable to load dependency " + lib.getName(), e);
			}
	}

	private static class VaroDependency implements Dependency {

		private final Dependency dependency;
		private final LoadPolicy loadPolicy;

		private VaroDependency(Dependency dependency, LoadPolicy loadPolicy) {
			this.dependency = dependency;
			this.loadPolicy = loadPolicy;
		}

		private VaroDependency(Dependency dependency) {
			this.dependency = dependency;
			this.loadPolicy = config -> true;
		}

		@Override
		public void load(Plugin plugin) throws Throwable {
			if(loadPolicy.shouldLoad(((VaroPlugin) plugin).getVaroConfig())) {
				plugin.getLogger().info("Loading " + this.getName());
				this.dependency.load(plugin);
			}
		}

		@Override
		public String getName() {
			return this.dependency.getName();
		}

		@Override
		public File getFile() {
			return this.dependency.getFile();
		}

		@Override
		public URL getUrl() throws MalformedURLException {
			return this.dependency.getUrl();
		}

		@Override
		public boolean isLoaded() {
			return this.dependency.isLoaded();
		}
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
