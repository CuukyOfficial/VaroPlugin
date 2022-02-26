package de.cuuky.varo.data.plugin;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Bukkit;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class LibraryLoader {

	private static final Collection<Library> LIBRARIES = new ArrayList<>();

	static {
		LIBRARIES.add(new JarLibrary("JDA-4.4.0_351", "net.dv8tion.jda.api.JDABuilder",
				"https://ci.dv8tion.net/job/JDA/351/artifact/build/libs/JDA-4.4.0_351-withDependencies-no-opus.jar",
				ConfigSetting.DISCORDBOT_ENABLED));
		LIBRARIES.add(new JarLibrary("gson-2.8.7", "com.google.gson.JsonElement",
				"https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.7/gson-2.8.7.jar"));
		LIBRARIES.add(new JarLibrary("guava-19.0.jar", "com.google.common.hash.Hashing",
				"https://repo1.maven.org/maven2/com/google/guava/guava/19.0/guava-19.0.jar"));
		LIBRARIES.add(new JarLibrary("java-telegram-bot-api-5.4.0", "com.pengrad.telegrambot.TelegramBot",
				"https://repo1.maven.org/maven2/com/github/pengrad/java-telegram-bot-api/5.4.0/java-telegram-bot-api-5.4.0.jar",
				ConfigSetting.TELEGRAM_ENABLED));
		LIBRARIES.add(new JarLibrary("okhttp-4.9.1", "okhttp3.OkHttp",
				"https://repo1.maven.org/maven2/com/squareup/okhttp3/okhttp/4.9.1/okhttp-4.9.1.jar",
				ConfigSetting.TELEGRAM_ENABLED));
		LIBRARIES.add(new JarLibrary("okio-2.8.0.jar", "okio.Buffer",
				"https://repo1.maven.org/maven2/com/squareup/okio/okio/2.8.0/okio-2.8.0.jar",
				ConfigSetting.TELEGRAM_ENABLED));
		LIBRARIES.add(new JarLibrary("kotlin-stdlib-1.4.10", "kotlin.jvm.internal.Intrinsics",
				"https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.4.10/kotlin-stdlib-1.4.10.jar",
				ConfigSetting.TELEGRAM_ENABLED));
		LIBRARIES.add(new LabymodPluginLibrary());
	}

	public LibraryLoader() {
		Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Checking for additional libraries to load...");
		for (Library lib : LIBRARIES)
			this.loadLibraryIfNecessary(lib);
		Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Finished loading libraries");
	}

	public void loadLibraryIfNecessary(Library lib) {
		if (lib.shouldLoad()) {
			Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Loading " + lib.getName());
			lib.load();
			if (lib.shouldLoad()) {
				Bukkit.getConsoleSender().sendMessage(Main.getConsolePrefix() + "Failed to load " + lib.getName());
			}
		} else
			Bukkit.getConsoleSender()
					.sendMessage(Main.getConsolePrefix() + "Library " + lib.getName() + " is not required");
	}
}
