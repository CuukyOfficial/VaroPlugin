package de.varoplugin.varo.listener;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        int slots = ConfigSetting.FAKE_MAX_SLOTS.getValueAsInt();
        if (slots != -1)
            event.setMaxPlayers(slots);

        if (!ConfigSetting.CHANGE_MOTD.getValueAsBoolean())
            return;

        if (!Main.getVaroGame().hasStarted()) {
            if (Bukkit.getServer().hasWhitelist())
                event.setMotd(Messages.MOTD_CLOSED.value());
            else
                event.setMotd(Messages.MOTD_OPEN.value());
            return;
        }

        if (ConfigSetting.ONLY_JOIN_BETWEEN_HOURS.getValueAsBoolean() && !Main.getDataManager().getOutsideTimeChecker().canJoin()) {
            event.setMotd(Messages.MOTD_CLOSED_HOURS.value());
            return;
        }

        event.setMotd(Messages.MOTD_OPEN.value());
    }
}
