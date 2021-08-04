package de.cuuky.varo.data.plugin;

import java.util.ArrayList;
import java.util.Collection;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class LibraryLoader {

	private static final Collection<Library> LIBRARIES = new ArrayList<>();

	static {
		LIBRARIES.add(new JarLibrary("JDA-4.3.0_304", "net.dv8tion.jda.api.JDABuilder",
				"https://ci.dv8tion.net/job/JDA/304/artifact/build/libs/JDA-4.3.0_304-withDependencies-no-opus.jar",
				ConfigSetting.DISCORDBOT_ENABLED));
	}

	public LibraryLoader() {
		System.out.println(Main.getConsolePrefix() + "Checking for additional librarys to load...");
		for (Library lib : LIBRARIES) {
			if (lib.shouldLoad()) {
				System.out.println(Main.getConsolePrefix() + "Loading " + lib.getName());
				lib.load();
			} else
				System.out.println(Main.getConsolePrefix() + "Library " + lib.getName() + " is not required");
		}
		System.out.println(Main.getConsolePrefix() + "Finished loading libraries");
	}
}
