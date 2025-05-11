package de.cuuky.varo.broadcast;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.varoplugin.cfw.version.VersionUtils;

public class Broadcaster {
    
    private static final String AD_MESSAGE = "§7-----------------------------------------\n"
            + "§7Du möchtest auch ein §5(OneDay)Varo §7veranstalten? Link zum Plugin: " + Main.DISCORD_INVITE
            + "\n§7-----------------------------------------";
    
    private long count;

	public Broadcaster() {
		if (ConfigSetting.SUPPORT_PLUGIN_ADS.getValueAsBoolean())
			startPluginAd();

		if (ConfigSetting.BROADCAST_INTERVAL_IN_SECONDS.isIntActivated())
		    starteSchedule();
	}

	private void starteSchedule() {
		int interval = ConfigSetting.BROADCAST_INTERVAL_IN_SECONDS.getValueAsInt() * 20;
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
		    for (VaroPlayer player : VaroPlayer.getOnlinePlayer()) {
                int size = Messages.BROADCAST.size(player);
                player.sendMessage(Messages.BROADCAST.value((int) (this.count % size), player));
            }
            this.count++;
		}, interval, interval);
	}

	private void startPluginAd() {
		int delay = (Main.getVaroGame().getPlayTime() * 60) > 0 ? (((Main.getVaroGame().getPlayTime() * 60) - 30) > 0 ? ((Main.getVaroGame().getPlayTime() * 60) - 30) * 20 : 900 * 20) : 900 * 20;
		Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (VersionUtils.getVersionAdapter().getOnlinePlayers().size() == 0)
                return;

            Bukkit.broadcastMessage(AD_MESSAGE);
        }, delay, delay);
	}
}
