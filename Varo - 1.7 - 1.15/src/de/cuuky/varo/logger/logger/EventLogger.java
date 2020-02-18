package de.cuuky.varo.logger.logger;

import java.awt.Color;

import de.cuuky.varo.bot.BotLauncher;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.logger.VaroLogger;
import de.cuuky.varo.utils.JavaUtils;

public class EventLogger extends VaroLogger {

	public enum LogType {

		ALERT("ALERT", Color.RED, ConfigEntry.DISCORDBOT_EVENT_ALERT),
		BORDER("BORDER", Color.GREEN, ConfigEntry.DISCORDBOT_EVENT_YOUTUBE),
		DEATH("DEATH", Color.BLACK, ConfigEntry.DISCORDBOT_EVENT_DEATH),
		JOIN_LEAVE("JOIN/LEAVE", Color.CYAN, ConfigEntry.DISCORDBOT_EVENT_JOIN_LEAVE),
		KILL("KILL", Color.BLACK, ConfigEntry.DISCORDBOT_EVENT_KILL),
		LOG("LOG", Color.RED, null),
		STRIKE("STRIKE", Color.YELLOW, ConfigEntry.DISCORDBOT_EVENT_STRIKE),
		WIN("WIN", Color.MAGENTA, ConfigEntry.DISCORDBOT_EVENT_WIN),
		YOUTUBE("YOUTUBE", Color.ORANGE, ConfigEntry.DISCORDBOT_EVENT_YOUTUBE);

		private Color color;
		private ConfigEntry idEntry;
		private String name;

		LogType(String name, Color color, ConfigEntry idEntry) {
			this.color = color;
			this.name = name;
			this.idEntry = idEntry;
		}

		public Color getColor() {
			return color;
		}

		public String getName() {
			return name;
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

	public EventLogger(String name) {
		super(name, true);
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

	private void sendToTelegram(LogType type, String message) {
		if(BotLauncher.getTelegramBot() == null)
			return;

		try {
			if(!type.equals(LogType.YOUTUBE))
				BotLauncher.getTelegramBot().sendEvent(message);
			else
				BotLauncher.getTelegramBot().sendVideo(message);
		} catch(ArrayIndexOutOfBoundsException e) {
			BotLauncher.getTelegramBot().sendEvent(message);
		}
	}

	public void println(LogType type, String message) {
		message = JavaUtils.replaceAllColors(message);

		String log = getCurrentDate() + " || " + "[" + type.getName() + "] " + message.replace("%noBot%", "");

		pw.println(log);
		logs.add(log);

		pw.flush();

		if(type.getPostChannel() == -1 || message.contains("%noBot%"))
			return;

		sendToDiscord(type, message);
		sendToTelegram(type, message);
	}
}