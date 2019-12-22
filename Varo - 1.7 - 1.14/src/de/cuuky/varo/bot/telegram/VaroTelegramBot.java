package de.cuuky.varo.bot.telegram;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import de.cuuky.varo.Main;
import de.cuuky.varo.bot.VaroBot;
import de.cuuky.varo.config.config.ConfigEntry;

public class VaroTelegramBot implements VaroBot {

	private static VaroTelegramBot instance;

	private TelegramBot telegrambot;

	private long eventChannelId, youtubeChannelId;

	private VaroTelegramBot() {
		eventChannelId = -1;
		youtubeChannelId = -1;

		telegrambot = new TelegramBot(ConfigEntry.TELEGRAM_BOT_TOKEN.getValueAsString());
	}

	public static VaroTelegramBot getInstance() {
		if (instance == null) {
			instance = new VaroTelegramBot();
		}
		return instance;
	}

	public static String getClassName() {
		return TelegramBot.class.getName();
	}

	@Override
	public void connect() {
		startListening();

		try {
			eventChannelId = ConfigEntry.TELEGRAM_EVENT_CHAT_ID.getValueAsLong();
		} catch (Exception e) {
			System.out.println(Main.getConsolePrefix() + "Could not get chat of chat id");
		}

		try {
			youtubeChannelId = ConfigEntry.TELEGRAM_VIDEOS_CHAT_ID.getValueAsLong();
		} catch (Exception e) {
			System.out.println(Main.getConsolePrefix() + "Could not get chat of videochat id");
		}

		System.out.println(Main.getConsolePrefix() + "Telegram Bot connected!");
	}

	private void startListening() {
		telegrambot.setUpdatesListener(new UpdatesListener() {

			@Override
			public int process(List<Update> args) {
				for (Update update : args) {
					if (update.message() == null)
						continue;

					if (!update.message().text().contains("/getId"))
						continue;

					telegrambot.execute(new SendMessage(update.message().chat().id(), "Chat ID von diesem Chat: " + update.message().chat().id()));
				}

				return UpdatesListener.CONFIRMED_UPDATES_ALL;
			}
		});
	}

	public void sendEvent(String message) {
		try {
			telegrambot.execute(new SendMessage(eventChannelId, message));
		} catch (Exception e) {
			System.out.println(Main.getConsolePrefix() + "Could not send events");
		}
	}

	public void sendVideo(String message) {
		try {
			telegrambot.execute(new SendMessage(youtubeChannelId, message));
		} catch (Exception e) {
			System.out.println(Main.getConsolePrefix() + "Could not send videos");
		}
	}

	@Override
	public void disconnect() {
		telegrambot.removeGetUpdatesListener();
	}
}