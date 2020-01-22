package de.cuuky.varo.broadcast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.version.VersionUtils;

public class Broadcaster {

	/*
	 * Partly old code
	 */

	private static Broadcaster instance;
	private ArrayList<String> messages;

	private Broadcaster() {
		if(ConfigEntry.SUPPORT_PLUGIN_ADS.getValueAsBoolean())
			startPluginAd();

		loadMessages();

		if(!ConfigEntry.BROADCAST_INTERVAL_IN_SECONDS.isIntActivated())
			return;

		starteSchedule();
	}

	private void loadMessages() {
		this.messages = new ArrayList<>();

		File file = new File("plugins/Varo", "broadcasts.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if(!file.exists()) {
			ArrayList<String> sb = new ArrayList<>();
			sb.add("&7Testnachricht Nummer 1");
			sb.add("&7Du kannst hier unendlich viele Nachrichten einfügen, die dann Random ausgewählt werden.");

			if(!cfg.contains("messages"))
				cfg.addDefault("messages", sb);
			cfg.options().copyDefaults(true);

			try {
				cfg.save(file);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

		messages.addAll(cfg.getStringList("messages"));
	}

	private void starteSchedule() {
		int interval = ConfigEntry.BROADCAST_INTERVAL_IN_SECONDS.getValueAsInt() * 20;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				int random = new Random().nextInt(((messages.size() - 1) - 0) + 1) + 0;
				Bukkit.broadcastMessage(ConfigMessages.getValue(messages.get(random)));
			}
		}, interval, interval);
	}

	private void startPluginAd() {
		int delay = (ConfigEntry.PLAY_TIME.getValueAsInt() * 60) > 0 ? (((ConfigEntry.PLAY_TIME.getValueAsInt() * 60) - 30) > 0 ? ((ConfigEntry.PLAY_TIME.getValueAsInt() * 60) - 30) * 20 : 900 * 20) : 900 * 20;
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if(VersionUtils.getOnlinePlayer().size() == 0)
					return;

				for(String m : getAdMessage())
					Bukkit.broadcastMessage(m.replaceAll("&", "§"));
			}
		}, delay, delay);
	}

	public static Broadcaster getInstance() {
		if(instance == null) {
			instance = new Broadcaster();
		}
		return instance;
	}

	private static String[] getAdMessage() {
		String[] messages = new String[] { "", "", "" };
		messages[0] = "&7-----------------------------------------";
		messages[1] = "&7Du möchtest auch ein &5(OneDay)Varo's &7veranstalten? Link zum Plugin: https://discord.gg/CnDSVVx";
		messages[2] = "&7-----------------------------------------";
		return messages;
	}
}