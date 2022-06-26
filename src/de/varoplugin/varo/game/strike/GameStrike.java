package de.varoplugin.varo.game.strike;

import de.varoplugin.varo.api.event.game.strike.VaroStrikeExecuteEvent;
import de.varoplugin.varo.api.event.game.strike.VaroStrikeInitializedEvent;
import de.varoplugin.varo.game.GameObject;
import de.varoplugin.varo.game.Varo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class GameStrike extends GameObject implements VaroStrike, Listener {

    private final VaroStrikeType type;
    private final VaroStrikable target;
    private boolean executed;

    public GameStrike(VaroStrikable target, VaroStrikeType type) {
        this.target = target;
        this.type = type;
    }

    @Override
    public void initialize(Varo varo) {
        super.initialize(varo);
        if (!this.executed) varo.getPlugin().getServer().getPluginManager().registerEvents(this, varo.getPlugin());
        varo.getPlugin().callEvent(new VaroStrikeInitializedEvent(this));
    }

    @Override
    public VaroStrikable getTarget() {
        return this.target;
    }

    @Override
    public VaroStrikeType getType() {
        return this.type;
    }

    @Override
    public boolean wasExecuted() {
        return this.executed;
    }

    @EventHandler
    public void onStrikeExecute(VaroStrikeExecuteEvent event) {
        if (!event.getStrike().equals(this)) return;
        this.executed = true;
        HandlerList.unregisterAll(this);
    }
}
