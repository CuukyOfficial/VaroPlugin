package de.cuuky.varo.logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.annotations.Expose;

import de.cuuky.varo.Main;
import de.cuuky.varo.logger.logger.BlockLogger;
import de.cuuky.varo.logger.logger.ChatLogger;
import de.cuuky.varo.logger.logger.ConsoleLogger;
import de.cuuky.varo.logger.logger.EventLogger;

public class VaroLoggerManager {

	private static final String REMOTE_URL = "https://varoplugin.de/log";
	public static final String RESTART_SIGNATURE = "0000000000000000000000000000000000000000000000000000000000000000";

	private final List<VaroLogger<?>> loggers;

	private final BlockLogger blockLogger;
	private final ChatLogger chatLogger;
	private final EventLogger eventLogger;

	public VaroLoggerManager() {
		this.loggers = new ArrayList<>();
		this.loggers.add(new ConsoleLogger("consoleLogs"));
		this.loggers.add(this.blockLogger = new BlockLogger("blocklogs"));
		this.loggers.add(this.chatLogger = new ChatLogger("chatlogs"));
		this.loggers.add(this.eventLogger = new EventLogger("logs"));

		try {
			for(VaroLogger<?> logger : this.loggers)
				logger.load();
		}catch(IOException e) {
			e.printStackTrace();
		}

		this.start();
	}

	//this is ugly
	private void start() {
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(REMOTE_URL);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");

					if(connection.getResponseCode() == 200) {
						Scanner sc = new Scanner(connection.getInputStream());
						if(sc.hasNextLine()) {
							RemoteData data = VaroLogger.GSON.fromJson(sc.nextLine(), RemoteData.class);
							byte[] bytes = data.salt.getBytes(StandardCharsets.UTF_8);
							for(VaroLogger<?> logger : VaroLoggerManager.this.loggers) {
								logger.setRemote(bytes);
								logger.print(RESTART_SIGNATURE, String.valueOf(data.timestamp));
							}
							sc.close();
							VaroLoggerManager.this.startQueue();
							return;
						}
						sc.close();
					}
				}catch(Throwable t) {
					t.printStackTrace();
				}
				for(VaroLogger<?> logger : VaroLoggerManager.this.loggers) {
					logger.setRemote(new byte[0]);
					logger.print(RESTART_SIGNATURE, "-error");
				}
				VaroLoggerManager.this.startQueue();
			}
		}.runTaskLaterAsynchronously(Main.getInstance(), 0L);
	}

	private void startQueue() {
		new BukkitRunnable() {
			@Override
			public void run() {
				synchronized (this) {
					VaroLoggerManager.this.loggers.forEach(VaroLogger::processQueue);
				}
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 20L, 20L * 10L);
	}

	public void cleanUp() {
		synchronized (this) {
			VaroLoggerManager.this.loggers.forEach(VaroLogger::cleanUp);
		}
	}

	public BlockLogger getBlockLogger() {
		return this.blockLogger;
	}

	public ChatLogger getChatLogger() {
		return this.chatLogger;
	}

	public EventLogger getEventLogger() {
		return this.eventLogger;
	}

	private class RemoteData {
		@Expose
		private long timestamp;
		@Expose
		private String salt;
	}
}