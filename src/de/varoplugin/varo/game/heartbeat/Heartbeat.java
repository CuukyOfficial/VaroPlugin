package de.varoplugin.varo.game.heartbeat;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.Listener;

public interface Heartbeat extends Listener {

    void initialize(Varo varo);

}