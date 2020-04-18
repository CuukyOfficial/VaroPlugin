package de.cuuky.varo.listener;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import de.cuuky.varo.configuration.placeholder.placeholder.util.DateInfo;
import de.cuuky.varo.utils.IPUtils;
import de.cuuky.varo.utils.varo.OutSideTimeChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;

public class ServerListPingListener implements Listener {

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		int slots = ConfigSetting.FAKE_MAX_SLOTS.getValueAsInt();
		if(slots != -1)
			event.setMaxPlayers(slots);

		if(ConfigSetting.CHANGE_MOTD.getValueAsBoolean()) {
			if(!Main.getVaroGame().hasStarted()) {
				if(Bukkit.getServer().hasWhitelist())
					event.setMotd(ConfigMessages.SERVER_MODT_NOT_OPENED.getValue());
				else
					event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				return;
			}

			if(!ConfigSetting.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean() || !Main.getVaroGame().hasStarted()) {
				event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				return;
			}

			if (ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_PLAYER_TIME.getValueAsBoolean()) {
				if (OutSideTimeChecker.canJoin(event.getAddress().getHostAddress())) {
					event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				} else {
					ZonedDateTime cal = IPUtils.ipToTime(event.getAddress().getHostAddress());
					String[] dates = cal.format(DateTimeFormatter.ofPattern("yyyy,MM,dd,HH,mm,ss")).split(",");

					event.setMotd(ConfigMessages.SERVER_MODT_CANT_JOIN_PLAYER.getValue().replace("%minHour%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%minMinute%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE1.getValueAsInt())).replace("%maxHour%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())).replace("%maxMinute%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE2.getValueAsInt())).replace("%currPlayerHour%", dates[DateInfo.HOUR]).replace("%currPlayerMin%", dates[DateInfo.MINUTES]).replace("%currPlayerSec%", dates[DateInfo.SECONDS]));
				}
			} else {
				if (OutSideTimeChecker.canJoin(null)) {
					event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				} else {
					String[] dates = OutSideTimeChecker.getTimesForPlayer(event.getAddress().getHostAddress());
					event.setMotd(ConfigMessages.SERVER_MODT_CANT_JOIN_GLOBAL.getValue().replace("%minHour%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%minMinute%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE1.getValueAsInt())).replace("%maxHour%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())).replace("%maxMinute%", String.format("%02d", ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_MINUTE2.getValueAsInt())).replace("%minPlayerHour%", dates[0]).replace("%minPlayerMinute%", dates[1]).replace("%maxPlayerHour%", dates[2]).replace("%maxPlayerMinute%", dates[3]));
				}
			}

		}
	}
}