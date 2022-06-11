package de.varoplugin.varo.dependencies;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import de.varoplugin.varo.VaroPlugin;
import de.varoplugin.varo.dependencies.Dependency.ClassPolicy;
import de.varoplugin.varo.dependencies.Dependency.ConfigEntryPolicy;

public class Dependencies {
	
	private static final String MAVEN_CENTERAL = "https://repo1.maven.org/maven2/";
	private static final String VAROPLUGIN_NEXUS = "https://repo.varoplugin.de/repository/maven-public/";
	
	public final static Dependency GUAVA = new JarDependency("Guava-19.0",
			MAVEN_CENTERAL + "com/google/guava/guava/19.0/guava-19.0.jar", "", new ClassPolicy("com.google.common.hash.Hashing"));
	public final static Dependency SIMPLE_YAML = new NoInitDependency("SimpleYAML-@SimpleYaml_version@",
			VAROPLUGIN_NEXUS + "me/carleslc/Simple-YAML/Simple-Yaml/@SimpleYaml_version@/Simple-Yaml-@SimpleYaml_version@.jar", "@SimpleYaml_hash@");
	public final static Dependency SNAKE_YAML = new NoInitDependency("SnakeYAML-@SnakeYaml_version@",
			MAVEN_CENTERAL + "org/yaml/snakeyaml/@SnakeYaml_version@/snakeyaml-@SnakeYaml_version@.jar", "@SnakeYaml_hash@");

	private static final Collection<Dependency> OPTIONAL_DEPENDENCIES = new ArrayList<>();

	static {
		OPTIONAL_DEPENDENCIES.add(new JarDependency("JDA-@JDA_version@", VAROPLUGIN_NEXUS + "net/dv8tion/JDA/@JDA_version@/JDA-@JDA_version@.jar", "@JDA_hash@",
				new ConfigEntryPolicy("net.dv8tion.jda.api.JDABuilder", config -> config.bot_discord_enabled)));
	}

	public static void loadNeeded(VaroPlugin varo) throws IOException, InvalidSignatureException {
		for (Dependency lib : OPTIONAL_DEPENDENCIES)
			lib.load(varo);
	}
}
