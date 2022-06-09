package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.tasks.register.VaroPlayerTaskInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class PlayerOnlineTrigger<I extends VaroPlayerTaskInfo> extends PlayerStateTrigger<I> {

    protected final boolean online;

    public PlayerOnlineTrigger(boolean online) {
        this.online = online;
        this.addCheck(VaroPlayerCheck.ONLINE, () -> this.getInfo().getPlayer().isOnline() == online);
    }

    private void checkEvent(PlayerEvent event, boolean change) {
        if (this.shallIgnore(event)) return;
        if (this.online == change) this.registerTasksActivated(VaroPlayerCheck.ONLINE);
        else this.unregisterTasks();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.checkEvent(event, true);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        this.checkEvent(event, false);
    }
}