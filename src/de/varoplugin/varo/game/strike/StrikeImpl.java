package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.api.event.game.strike.VaroStrikeExecuteEvent;
import de.varoplugin.varo.api.event.game.strike.VaroStrikeInitializedEvent;
import de.varoplugin.varo.game.UniqueGameObject;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.Player;
import org.bukkit.event.Listener;

import java.util.UUID;

public class StrikeImpl extends UniqueGameObject implements Strike, Listener {

    private final StrikeType type;
    private final Player target;
    private boolean executed;

    StrikeImpl(UUID uuid, Player target, StrikeType type) {
        super(uuid);
        this.target = target;
        this.type = type;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new VaroStrikeInitializedEvent(this));
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
        if (this.executed || !this.getVaro().getPlugin().isCancelled(new VaroStrikeExecuteEvent(this))) return;
        this.executed = true;
    }

    @Override
    public boolean wasExecuted() {
        return this.executed;
    }
}
