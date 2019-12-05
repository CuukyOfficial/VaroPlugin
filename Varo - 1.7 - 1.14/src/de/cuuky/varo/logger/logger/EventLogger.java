package de.cuuky.varo.logger.logger;

import java.awt.Color;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.bot.telegram.VaroTelegramBot;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.logger.Logger;
import de.cuuky.varo.utils.Utils;

public class EventLogger extends Logger {

	private static EventLogger instance;

	public enum LogType {

		STRIKE("STRIKE", Color.YELLOW, ConfigEntry.DISCORDBOT_EVENT_STRIKE),
		JOIN_LEAVE("JOIN/LEAVE", Color.CYAN, ConfigEntry.DISCORDBOT_EVENT_JOIN_LEAVE),
		WIN("WIN", Color.MAGENTA, ConfigEntry.DISCORDBOT_EVENT_WIN),
		BORDER("BORDER", Color.GREEN, ConfigEntry.DISCORDBOT_EVENT_YOUTUBE),
		KILL("KILL", Color.BLACK, ConfigEntry.DISCORDBOT_EVENT_KILL),
		DEATH("DEATH", Color.BLACK, ConfigEntry.DISCORDBOT_EVENT_DEATH),
		ALERT("ALERT", Color.RED, ConfigEntry.DISCORDBOT_EVENT_ALERT),
		YOUTUBE("YOUTUBE", Color.ORANGE, ConfigEntry.DISCORDBOT_EVENT_YOUTUBE),
		LOG("LOG", Color.RED, null);

		private String name;
		private Color color;
		private ConfigEntry idEntry;

		LogType(String name, Color color, ConfigEntry idEntry) {
			this.color = color;
			this.name = name;
			this.idEntry = idEntry;
		}

		public String getName() {
			return name;
		}

		public Color getColor() {
			return color;
		}

		public long getPostChannel() {
			if(idEntry == null || BotLauncher.getDiscordBot() == null || !BotLauncher.getDiscordBot().isEnabled())
				return -1;

			try {
				idEntry.getValueAsLong();
			} catch(IllegalArgumentException e) {
				return ConfigEntry.DISCORDBOT_EVENTCHANNELID.getValueAsLong();
			}

			return idEntry.getValueAsLong();
		}

		public static LogType getType(String s) {
			for(LogType type : values())
				if(type.getName().equalsIgnoreCase(s))
					return type;

			return null;
		}
	}

	private EventLogger(String name) {
		super(name, true);
	}

	public static EventLogger getInstance() {
		if (instance == null) {
			instance = new EventLogger("logs");
		}
		return instance;
	}

	public void println(LogType type, String message) {
		message = Utils.replaceAllColors(message);

		String log = getCurrentDate() + " || " + "[" + type.getName() + "] " + message.replaceAll("%noBot%", "");

		pw.println(log);
		logs.add(log);

		pw.flush();

		if(type.getPostChannel() == -1 || message.contains("%noBot%"))
			return;

		sendToDiscord(type, message);
		sendToTelegram(type, message);
	}

	private void sendToTelegram(LogType type, String message) {
		if(VaroTelegramBot.getInstance() == null)
			return;

		try {
			if(!type.equals(LogType.YOUTUBE))
				VaroTelegramBot.getInstance().sendEvent(message);
			else
				VaroTelegramBot.getInstance().sendVideo(message);
		} catch(ArrayIndexOutOfBoundsException e) {
			VaroTelegramBot.getInstance().sendEvent(message);
		}
	}

	private void sendToDiscord(LogType type, String msg) {
		if(type.getPostChannel() == -1 || BotLauncher.getDiscordBot() == null || !BotLauncher.getDiscordBot().isEnabled())
			return;

		try {
			BotLauncher.getDiscordBot().sendMessage(msg, type.getName(), type.getColor(), type.getPostChannel());
		} catch(NoClassDefFoundError | BootstrapMethodError e) {
			return;
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
	}
}