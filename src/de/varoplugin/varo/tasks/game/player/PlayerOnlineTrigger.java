package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.register.VaroPlayerTaskInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class PlayerOnlineTrigger<I extends VaroPlayerTaskInfo> extends PlayerTrigger<I> {

    private final boolean online;

    @SafeVarargs
    public PlayerOnlineTrigger(boolean online, VaroTask<I>... tasks) {
        super(tasks);

        this.online = online;
    }

    @Override
    public boolean shouldEnable() {
        return this.getInfo().getPlayer().isOnline() == this.online;
    }

    private void checkEvent(PlayerEvent event, boolean change) {
        if (this.shallIgnore(event)) return;
        if (this.online == change) this.registerTasks();
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