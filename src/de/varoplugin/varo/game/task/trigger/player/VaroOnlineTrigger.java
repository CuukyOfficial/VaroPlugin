package de.varoplugin.varo.game.task.trigger.player;

import de.varoplugin.varo.api.task.trigger.Trigger;
import de.varoplugin.varo.game.entity.player.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VaroOnlineTrigger extends AbstractPlayerTrigger {

    private boolean online;

    public VaroOnlineTrigger(Player player, boolean online, boolean match) {
        super(player, match);
        this.online = online;
    }

    public VaroOnlineTrigger(Player player, boolean online) {
        this(player, online, true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        this.triggerIf(this.online);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!this.getPlayer().isPlayer(event.getPlayer())) return;
        this.triggerIf(!this.online);
    }

    @Override
    protected boolean isTriggered() {
        return this.getPlayer().isOnline() == this.online;
    }

    @Override
    public Trigger clone() {
        VaroOnlineTrigger trigger = (VaroOnlineTrigger) super.clone();
        trigger.online = this.online;
        return trigger;
    }
}
