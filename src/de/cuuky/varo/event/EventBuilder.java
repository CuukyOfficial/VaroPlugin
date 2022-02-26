package de.cuuky.varo.event;

import de.cuuky.varo.Varo;

import java.util.function.Function;

public interface EventBuilder extends Function<Varo, VaroEvent> {
}