package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;

public class ServerListPingListener implements Listener {

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		int slots = ConfigSetting.FAKE_MAX_SLOTS.getValueAsInt();
		if (slots != -1)
			event.setMaxPlayers(slots);

		if (ConfigSetting.CHANGE_MOTD.getValueAsBoolean()) {
			if (!Main.getVaroGame().hasStarted()) {
				if (Bukkit.getServer().hasWhitelist())
					event.setMotd(ConfigMessages.SERVER_MODT_NOT_OPENED.getValue());
				else
					event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				return;
			}

			if (!ConfigSetting.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean() || Main.getDataManager().getOutsideTimeChecker().canJoin() || !Main.getVaroGame().hasStarted()) {
				event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				return;
			}

			if (!Main.getDataManager().getOutsideTimeChecker().canJoin())
				event.setMotd(ConfigMessages.SERVER_MODT_CANT_JOIN_HOURS.getValue().replace("%minHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%maxHour%", String.valueOf(ConfigSetting.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())));
		}
	}
}