package de.varoplugin.varo.player.event;

import java.util.Arrays;
import java.util.List;

import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.events.DeathEvent;
import de.varoplugin.varo.player.event.events.DeathNoLivesEvent;
import de.varoplugin.varo.player.event.events.JoinEvent;
import de.varoplugin.varo.player.event.events.KickEvent;
import de.varoplugin.varo.player.event.events.KillEvent;
import de.varoplugin.varo.player.event.events.QuitEvent;

public class BukkitEvent {

    private static final List<BukkitEvent> events = Arrays.asList(new DeathEvent(),
        new DeathNoLivesEvent(), new KickEvent(), new JoinEvent(), new QuitEvent(), new KillEvent());

    protected BukkitEventType eventType;

    protected BukkitEvent(BukkitEventType eventType) {
        this.eventType = eventType;
    }

    public BukkitEvent(VaroPlayer player, BukkitEventType eventType) {
        for (BukkitEvent event : events)
            if (event.getEventType().equals(eventType))
                event.onExec(player);
    }

    public BukkitEventType getEventType() {
        return eventType;
    }

    public void onExec(VaroPlayer player) {
    }
}