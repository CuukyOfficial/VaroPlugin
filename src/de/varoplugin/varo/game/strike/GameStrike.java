package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.api.event.game.strike.VaroStrikeExecuteEvent;
import de.varoplugin.varo.api.event.game.strike.VaroStrikeInitializedEvent;
import de.varoplugin.varo.game.GameObject;
import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.entity.player.VaroPlayer;
import org.bukkit.event.Listener;

public class GameStrike extends GameObject implements VaroStrike, Listener {

    private final VaroStrikeType type;
    private final VaroPlayer target;
    private boolean executed;

    public GameStrike(VaroPlayer target, VaroStrikeType type) {
        this.target = target;
        this.type = type;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        varo.getPlugin().callEvent(new VaroStrikeInitializedEvent(this));
    }

    @Override
    public VaroPlayer getTarget() {
        return this.target;
    }

    @Override
    public VaroStrikeType getType() {
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
