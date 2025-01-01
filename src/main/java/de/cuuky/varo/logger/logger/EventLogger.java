package de.cuuky.varo.logger.logger;

import java.awt.Color;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.logger.CachedVaroLogger;

public class EventLogger extends CachedVaroLogger<String> {

	public enum LogType {

		ALERT("ALERT", Color.RED, ConfigSetting.DISCORDBOT_EVENT_ALERT),
		BORDER("BORDER", Color.GREEN, ConfigSetting.DISCORDBOT_EVENT_BORDER),
		DEATH("DEATH", Color.BLACK, ConfigSetting.DISCORDBOT_EVENT_DEATH),
		JOIN_LEAVE("JOIN/LEAVE", Color.CYAN, ConfigSetting.DISCORDBOT_EVENT_JOIN_LEAVE),
		KILL("DEATH", Color.BLACK, ConfigSetting.DISCORDBOT_EVENT_KILL),
		LOG("LOG", Color.RED, null),
		STRIKE("STRIKE", Color.YELLOW, ConfigSetting.DISCORDBOT_EVENT_STRIKE),
		WIN("WIN", Color.MAGENTA, ConfigSetting.DISCORDBOT_EVENT_WIN),
		YOUTUBE("YOUTUBE", Color.ORANGE, ConfigSetting.DISCORDBOT_EVENT_YOUTUBE);

		private Color color;
		private ConfigSetting idEntry;
		private String name;

		LogType(String name, Color color, ConfigSetting idEntry) {
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
			if (idEntry == null)
				return -1;

			return idEntry.getValueAsLong() != -1 ? idEntry.getValueAsLong() : ConfigSetting.DISCORDBOT_EVENT_CHANNELID.getValueAsLong();
		}
	}

	private List<Object[]> queue; // wtf

	public EventLogger(String name) {
		super(name, String.class);

		this.queue = new CopyOnWriteArrayList<>();
		this.startSendQueue();
	}

	private void startSendQueue() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if (Main.getBotLauncher() == null)
					return;

				for (Object[] msg : queue) {
					sendToDiscord((LogType) msg[0], (String) msg[1], (UUID) msg[2]);
					queue.remove(msg);
				}
			}
		}.runTaskTimer(Main.getInstance(), 20L, 20L);
	}

	private boolean sendToDiscord(LogType type, String msg, UUID playerUuid) {
		if (Main.getBotLauncher().getDiscordbot() == null)
			return true;

		try {
			Main.getBotLauncher().getDiscordbot().sendMessage(msg, type.getName(), null, playerUuid != null && ConfigSetting.DISCORDBOT_SHOW_PLAYER_HEADS.getValueAsBoolean() ? "https://minotar.net/helm/" + playerUuid.toString() + "/32.png".replace("-", "") : null, null, type.getColor(), type.getPostChannel());
			return true;
		} catch (NoClassDefFoundError | BootstrapMethodError e) {
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(Main.getPrefix() + "Failed to broadcast message! Did you enter a wrong channel ID?");
			return false;
		}
	}

	public void println(LogType type, String message, UUID playerUuid) {
        message = ChatColor.stripColor(message.replace("&", "§"));

        String log = getCurrentDate() + " || " + "[" + type.getName() + "] " + message.replace("%noDiscord%", "").replace("%noBot%", "");
        this.queueLog(log);

        if (message.contains("%noBot%") || message.contains("%noDiscord%"))
            return;

        if (Main.getBotLauncher() == null) {
            queue.add(new Object[] { type, message, playerUuid });
            return;
        }

        sendToDiscord(type, message, playerUuid);
    }

	public void println(LogType type, String message) {
		this.println(type, message, null);
	}
}