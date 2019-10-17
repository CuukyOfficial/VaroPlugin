package de.cuuky.varo.broadcast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.DefaultReplace;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.utils.Utils;
import de.cuuky.varo.version.VersionUtils;

public class Broadcaster {
	
	/*
	 * Partly old code
	 */

	private ArrayList<String> messages = new ArrayList<>();

	public Broadcaster() {
		if(ConfigEntry.SUPPORT_PLUGIN_ADS.getValueAsBoolean()) {
			int delay = (ConfigEntry.PLAY_TIME.getValueAsInt() * 60) > 0 ? (((ConfigEntry.PLAY_TIME.getValueAsInt() * 60) - 30) > 0 ? ((ConfigEntry.PLAY_TIME.getValueAsInt() * 60) - 30) * 20 : 900 * 20) : 900 * 20;
			Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

				@Override
				public void run() {
					if(VersionUtils.getOnlinePlayer().size() == 0)
						return;

					for(String m : getRandomAd())
						Bukkit.broadcastMessage(m.replaceAll("&", "ß"));
				}
			}, delay, delay);
		}

		loadMessages();

		if(!ConfigEntry.BROADCAST_INTERVAL_IN_SECONDS.isIntActivated())
			return;

		starteSchedule();
	}

	private void loadMessages() {
		File file = new File("plugins/Varo", "broadcasts.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		if(!file.exists()) {
			ArrayList<String> sb = new ArrayList<>();
			sb.add("&7Testnachricht Nummer 1");
			sb.add("&7Du kannst hier unendlich viele Nachrichten einfuegen, die dann Random ausgew√§hlt werden.");

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
				Bukkit.broadcastMessage(new DefaultReplace(messages.get(random)).getReplaced());
			}
		}, interval, interval);
	}

	private static String[] getRandomAd() {
		int random = Utils.randomInt(0, 1);
		String[] messages = null;
		if(random == 0) {
			messages = new String[] { "", "", "" };
			messages[0] = "ß7-----------------------------------------";
			messages[1] = "&7Du m√∂chtest auch ein &5(OneDay)Varo's &7veranstalten? Link zum Plugin: https://discord.gg/CnDSVVx";
			messages[2] = "ß7-----------------------------------------";
		} else {
			messages = new String[] { "", "", "", "" };
			messages[0] = "ß7-----------------------------------------";
			messages[1] = "&7Du hast Lust auf OneDayVaros? &7Link: https://discord.gg/UPCZ9eX";
			messages[2] = "&cLink zum normalen VaroPlugin-Discord: &7https://discord.gg/CnDSVVx";
			messages[3] = "ß7-----------------------------------------";
		}
		return messages;
	}
}
