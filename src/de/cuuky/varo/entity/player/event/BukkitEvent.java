package de.cuuky.varo.entity.player.event;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.events.DeathEvent;
import de.cuuky.varo.entity.player.event.events.DeathNoLifesEvent;
import de.cuuky.varo.entity.player.event.events.JoinEvent;
import de.cuuky.varo.entity.player.event.events.KickEvent;
import de.cuuky.varo.entity.player.event.events.KillEvent;
import de.cuuky.varo.entity.player.event.events.QuitEvent;

import java.util.Arrays;
import java.util.List;

public class BukkitEvent {

    private static final List<BukkitEvent> events = Arrays.asList(new DeathEvent(),
        new DeathNoLifesEvent(), new KickEvent(), new JoinEvent(), new QuitEvent(), new KillEvent());

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