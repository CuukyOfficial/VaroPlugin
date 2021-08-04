package de.cuuky.varo.data.plugin;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class LibraryLoader {

	private static final Collection<Library> LIBRARIES = new ArrayList<>();

	static {
		LIBRARIES.add(new JarLibrary("JDA-4.3.0_304", "net.dv8tion.jda.api.JDABuilder",
				"https://ci.dv8tion.net/job/JDA/304/artifact/build/libs/JDA-4.3.0_304-withDependencies-no-opus.jar",
				ConfigSetting.DISCORDBOT_ENABLED));
		LIBRARIES.add(new JarLibrary("gson-2.8.7", "com.google.gson.JsonElement",
				"https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.7/gson-2.8.7.jar"));
		LIBRARIES.add(new JarLibrary("guava-19.0.jar", "com.google.common.hash.Hashing",
				"https://repo1.maven.org/maven2/com/google/guava/guava/19.0/guava-19.0.jar"));
		LIBRARIES.add(new JarLibrary("java-telegram-bot-api-5.2.0", "com.pengrad.telegrambot.TelegramBot",
				"https://repo1.maven.org/maven2/com/github/pengrad/java-telegram-bot-api/5.2.0/java-telegram-bot-api-5.2.0.jar",
				ConfigSetting.TELEGRAM_ENABLED));
		LIBRARIES.add(new LabymodPluginLibrary());
	}

	public LibraryLoader() {
		Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Checking for additional librarys to load...");
		for (Library lib : LIBRARIES)
			this.loadLibraryIfNecessary(lib);
		Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Finished loading libraries");
	}

	public void loadLibraryIfNecessary(Library lib) {
		if (lib.shouldLoad()) {
			Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Loading " + lib.getName());
			lib.load();
		} else
			Bukkit.getConsoleSender()
					.sendMessage(Main.getConsolePrefix() + "Library " + lib.getName() + " is not required");
	}
}
