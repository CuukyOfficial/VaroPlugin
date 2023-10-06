package de.cuuky.varo.broadcast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.cfw.configuration.YamlConfigurationUtil;
import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class Broadcaster {

	/*
	 * Partly old code
	 */

	private ArrayList<String> messages;

	public Broadcaster() {
		if (ConfigSetting.SUPPORT_PLUGIN_ADS.getValueAsBoolean())
			startPluginAd();

		loadMessages();

		if (!ConfigSetting.BROADCAST_INTERVAL_IN_SECONDS.isIntActivated() || this.messages.isEmpty())
			return;

		starteSchedule();
	}

	private void loadMessages() {
		this.messages = new ArrayList<>();

		File file = new File("plugins/Varo/config", "broadcasts.yml");

		YamlConfiguration cfg;
		if (!file.exists()) {
			cfg = new YamlConfiguration();

			ArrayList<String> sb = new ArrayList<>();
			sb.add("&7Testnachricht Nummer 1");
			sb.add("&7Du kannst hier unendlich viele Nachrichten einfuegen, die dann Random ausgewaehlt werden.");

			if (!cfg.contains("messages"))
				cfg.addDefault("messages", sb);
			cfg.options().copyDefaults(true);
			YamlConfigurationUtil.save(cfg, file);
		} else
			cfg = YamlConfigurationUtil.loadConfiguration(file);

		messages.addAll(cfg.getStringList("messages"));
	}

	private void starteSchedule() {
		int interval = ConfigSetting.BROADCAST_INTERVAL_IN_SECONDS.getValueAsInt() * 20;
		new BukkitRunnable() {

			@Override
			public void run() {
				int random = new Random().nextInt(messages.size());
				Bukkit.broadcastMessage(Main.getLanguageManager().replaceMessage(messages.get(random)));
			}
		}.runTaskTimer(Main.getInstance(), interval, interval);
	}

	private void startPluginAd() {
		int delay = (ConfigSetting.PLAY_TIME.getValueAsInt() * 60) > 0 ? (((ConfigSetting.PLAY_TIME.getValueAsInt() * 60) - 30) > 0 ? ((ConfigSetting.PLAY_TIME.getValueAsInt() * 60) - 30) * 20 : 900 * 20) : 900 * 20;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (VersionUtils.getVersionAdapter().getOnlinePlayers().size() == 0)
                    return;

                for (String m : getAdMessage())
                    Bukkit.broadcastMessage(m.replaceAll("&", "§"));
            }
        }.runTaskTimer(Main.getInstance(), delay, delay);
	}

	private static String[] getAdMessage() {
		String[] messages = new String[] { "", "", "" };
		messages[0] = "&7-----------------------------------------";
		messages[1] = "&7Du moechtest auch ein &5(OneDay)Varo &7veranstalten? Link zum Plugin: " + Main.DISCORD_INVITE;
		messages[2] = "&7-----------------------------------------";
		return messages;
	}
}