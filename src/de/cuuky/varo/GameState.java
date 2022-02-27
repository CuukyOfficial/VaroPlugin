package de.cuuky.varo;

import java.util.function.Function;

public interface GameState extends Function<Varo, Heartbeat> {

    boolean eventsAllowed();

}