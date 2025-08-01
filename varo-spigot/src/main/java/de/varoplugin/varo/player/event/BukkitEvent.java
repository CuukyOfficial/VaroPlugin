package de.varoplugin.varo.player.event;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.events.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class BukkitEvent {

    private static final List<BukkitEvent> events = Arrays.asList(new DeathEvent(),
        new DeathNoLivesEvent(), new KickEvent(), new JoinEvent(), new QuitEvent(), new KillEvent(), new WinEvent());

    protected BukkitEventType eventType;

    protected BukkitEvent(BukkitEventType eventType) {
        this.eventType = eventType;
    }

    // why tf is this a constructor???
    public BukkitEvent(VaroPlayer player, BukkitEventType eventType) {
        for (BukkitEvent event : events)
            if (event.getEventType() == eventType)
                try {
                    event.onExec(player);
                } catch (Throwable t) {
                    Main.getInstance().getLogger().log(Level.SEVERE, "Error while executing event", t);
                }
    }

    public BukkitEventType getEventType() {
        return eventType;
    }

    public void onExec(VaroPlayer player) {
    }
}