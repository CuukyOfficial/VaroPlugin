package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.tasks.VaroTask;
import de.varoplugin.varo.tasks.register.VaroPlayerTaskInfo;
import org.bukkit.event.player.PlayerEvent;

public interface VaroPlayerTask<I extends VaroPlayerTaskInfo> extends VaroTask<I> {

    default boolean shallIgnore(PlayerEvent event) {
        return !event.getPlayer().getUniqueId().equals(this.getInfo().getPlayer().getUuid());
    }
}