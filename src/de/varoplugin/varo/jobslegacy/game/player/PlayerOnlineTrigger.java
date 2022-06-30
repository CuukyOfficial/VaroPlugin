package de.varoplugin.varo.jobslegacy.game.player;

import de.varoplugin.varo.game.entity.player.VaroPlayer;
import de.varoplugin.varo.jobslegacy.VaroJob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerOnlineTrigger extends PlayerTrigger {

    private final boolean online;

    public PlayerOnlineTrigger(VaroPlayer player, boolean online, VaroJob... tasks) {
        super(player, tasks);

        this.online = online;
    }

    @Override
    public boolean shouldEnable() {
        return this.getPlayer().isOnline() == this.online;
    }

    private void checkEvent(PlayerEvent event, boolean change) {
        if (!this.getPlayer().getUuid().equals(event.getPlayer().getUniqueId())) return;
        if (this.online == change) this.registerJobs();
        else this.unregisterJobs();
    }

    @EventHandler()
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.checkEvent(event, true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        this.checkEvent(event, false);
    }
}