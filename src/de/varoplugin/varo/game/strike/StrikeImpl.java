package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.api.event.game.strike.StrikeExecuteEvent;
import de.varoplugin.varo.api.event.game.strike.StrikeInitializedEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

final class StrikeImpl extends UniqueGameObject implements Strike, Listener {

    private final StrikeType type;
    private Player target;
    private boolean executed;

    StrikeImpl(UUID uuid, StrikeType type) {
        super(uuid);
        this.type = type;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new StrikeInitializedEvent(this));
    }

    @Override
    public void setTarget(Player target) {
        this.target = target;
    }

    @Override
    public Player getTarget() {
        return this.target;
    }

    @Override
    public StrikeType getType() {
        return this.type;
    }

    @Override
    public void execute() {
        if (this.executed || !this.getVaro().getPlugin().isCancelled(new StrikeExecuteEvent(this))) return;
        this.executed = true;
    }

    @Override
    public boolean wasExecuted() {
        return this.executed;
    }
}
