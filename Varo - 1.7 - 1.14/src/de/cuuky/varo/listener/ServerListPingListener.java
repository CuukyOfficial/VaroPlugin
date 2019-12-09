package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.threads.OutSideTimeChecker;

public class ServerListPingListener implements Listener {

	@EventHandler
	public void onServerListPing(ServerListPingEvent event) {
		int slots = ConfigEntry.FAKE_MAX_SLOTS.getValueAsInt();
		if(slots != -1)
			event.setMaxPlayers(slots);

		if(ConfigEntry.CHANGE_MOTD.getValueAsBoolean()) {
			if(!Game.getInstance().hasStarted()) {
				if(Bukkit.getServer().hasWhitelist())
					event.setMotd(ConfigMessages.SERVER_MODT_NOT_OPENED.getValue());
				else
					event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				return;
			}

			if(!ConfigEntry.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean() || OutSideTimeChecker.getInstance().canJoin() || !Game.getInstance().hasStarted()) {
				event.setMotd(ConfigMessages.SERVER_MODT_OPEN.getValue());
				return;
			}

			if(!OutSideTimeChecker.getInstance().canJoin())
				event.setMotd(ConfigMessages.SERVER_MODT_CANT_JOIN_HOURS.getValue().replace("%minHour%", String.valueOf(ConfigEntry.ONLY_JOIN_BETWEEN_HOURS_HOUR1.getValueAsInt())).replace("%maxHour%", String.valueOf(ConfigEntry.ONLY_JOIN_BETWEEN_HOURS_HOUR2.getValueAsInt())));
		}
	}
}