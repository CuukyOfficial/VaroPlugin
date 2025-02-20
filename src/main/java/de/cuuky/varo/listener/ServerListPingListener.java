package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public class ServerListPingListener implements Listener {

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		int slots = ConfigSetting.FAKE_MAX_SLOTS.getValueAsInt();
		if (slots != -1)
			event.setMaxPlayers(slots);

		if (ConfigSetting.CHANGE_MOTD.getValueAsBoolean()) {
			if (!Main.getVaroGame().hasStarted()) {
				if (Bukkit.getServer().hasWhitelist())
					event.setMotd(Messages.MOTD_OPEN.value());
				else
					event.setMotd(Messages.MOTD_CLOSED.value());
				return;
			}

			if (!ConfigSetting.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean() || Main.getDataManager().getOutsideTimeChecker().canJoin() || !Main.getVaroGame().hasStarted()) {
				event.setMotd(Messages.MOTD_OPEN.value());
				return;
			}

			if (!Main.getDataManager().getOutsideTimeChecker().canJoin())
			    event.setMotd(Messages.MOTD_CLOSED_HOURS.value());
		}
	}
}