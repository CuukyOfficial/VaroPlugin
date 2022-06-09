package de.varoplugin.varo.tasks.game.player;

import de.varoplugin.varo.api.event.game.player.VaroPlayerRemoveEvent;
import de.varoplugin.varo.game.VaroState;
import de.varoplugin.varo.tasks.AbstractTaskTrigger;
import de.varoplugin.varo.tasks.VaroTaskTrigger;
import de.varoplugin.varo.tasks.VaroTriggerCheckType;
import de.varoplugin.varo.tasks.game.VaroStateTrigger;
import de.varoplugin.varo.tasks.register.VaroPlayerTaskInfo;
import org.bukkit.event.EventHandler;

/**
 * Triggers all player specific tasks.
 * Unregisters the task on player remove.
 *
 * @author CuukyOfficial
 * @version v0.1
 */
public class PlayerStateTrigger<I extends VaroPlayerTaskInfo> extends AbstractTaskTrigger<I> implements VaroPlayerTrigger<I> {

    public enum VaroPlayerCheck implements VaroTriggerCheckType {
        PARTICIPANT_STATE,
        MODE,
        ONLINE
    }

    protected PlayerStateTrigger() {}

    @SafeVarargs
    public PlayerStateTrigger(VaroState state, VaroTaskTrigger<I>... triggers) {
        super(triggers);

        this.hook(new VaroStateTrigger<>(state));
    }

    @EventHandler
    public void onPlayerRemove(VaroPlayerRemoveEvent event) {
        if (!event.getPlayer().equals(this.getInfo().getPlayer())) return;
        this.unregister();
    }
}