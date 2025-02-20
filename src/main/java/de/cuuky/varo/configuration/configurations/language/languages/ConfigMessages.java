package de.cuuky.varo.configuration.configurations.language.languages;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.languages.DefaultLanguage;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public enum ConfigMessages implements DefaultLanguage {

	PLACEHOLDER_NO_TOP_PLAYER("placeholder.noTopPlayer", "-"), // TODO delete this
	PLACEHOLDER_NO_TOP_TEAM("placeholder.noTopTeam", "-"), // TODO delete this

	MODS_BLOCKED_MODS_KICK("mods.blockedModsKick", "&7Bitte entferne folgende Mods: %colorcode%%mods%"),
	MODS_BLOCKED_MODLIST_SPLIT("mods.blockedModsListSplit", "&7, "),
	MODS_BLOCKED_MODS_BROADCAST("mods.blockedModsBroadcast", "&7Der Spieler %colorcode%%player% &7hat versucht mit folgenden blockierten Mods zu joinen: %colorcode%%mods%");

	private String path, defaultMessage, message;

	ConfigMessages(String path, String message) {
		this.path = path;
		this.defaultMessage = message;
		this.message = message;
	}

	private String getMessage(Language lang) {
		String message;
		if (lang == null || lang == Main.getLanguageManager().getDefaultLanguage() || !ConfigSetting.MAIN_LANGUAGE_ALLOW_OTHER.getValueAsBoolean())
			message = Main.getLanguageManager().getDefaultLanguage().getMessage(this.path);
		else {
			String langMsg = lang.getMessage(this.path);
			message = langMsg == null ? Main.getLanguageManager().getDefaultLanguage().getMessage(this.path) : langMsg;
		}

		return Main.getLanguageManager().replaceMessage(message != null ? message : this.message);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getDefaultMessage() {
		return this.defaultMessage;
	}

	@Override
	public String getValue() {
		return getMessage(Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder) {
		return getMessage(localeHolder != null && localeHolder.getLocale() != null ? Main.getLanguageManager().getLanguages().get(localeHolder.getLocale()) : Main.getLanguageManager().getDefaultLanguage());
	}

	@Override
	public String getValue(CustomPlayer localeHolder, CustomPlayer replace) {
		return Main.getLanguageManager().replaceMessage(getValue(localeHolder), replace);
	}
}
