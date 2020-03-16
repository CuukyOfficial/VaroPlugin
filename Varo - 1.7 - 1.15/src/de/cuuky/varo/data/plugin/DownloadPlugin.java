package de.cuuky.varo.data.plugin;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.listener.PermissionSendListener;

public enum DownloadPlugin {

	DISCORDBOT(66778, "Discordbot", "net.dv8tion.jda.api.JDABuilder", ConfigSetting.DISCORDBOT_ENABLED),
	SERVER_LIBRARY_EXTENSION(76114, "ServerLibraryExtension", "com.google.gson.JsonElement"),
	TELEGRAM(66823, "Telegrambot", "com.pengrad.telegrambot.TelegramBot", ConfigSetting.TELEGRAM_ENABLED),
	LABYMOD(52423, "Labymod", "net.labymod.serverapi.LabyModAPI", ConfigSetting.DISABLE_LABYMOD_FUNCTIONS, ConfigSetting.KICK_LABYMOD_PLAYER, ConfigSetting.ONLY_LABYMOD_PLAYER);

	private int id;
	private String requiredClassName, name;
	private ConfigSetting[] configSettings;

	private DownloadPlugin(int id, String name, String requiredClassName, ConfigSetting... configSettings) {
		this.id = id;
		this.requiredClassName = requiredClassName;
		this.name = name;
		this.configSettings = configSettings;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}

	public String getRequiredClassName() {
		return this.requiredClassName;
	}
	
	public boolean shallLoad() {
		if(configSettings.length == 0)
			return true;
		
		for(ConfigSetting setting : configSettings)
			if(setting.getValueAsBoolean())
				return true;
		
		return false;
	}

	public void checkedAndLoaded() {
		switch(this) {
		case LABYMOD:
			Bukkit.getPluginManager().registerEvents(new PermissionSendListener(), Main.getInstance());
			break;
		default:
			break;
		}
	}
}