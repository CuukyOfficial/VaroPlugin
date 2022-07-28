package de.varoplugin.varo.game;

import java.util.Collection;

public interface VaroBuilder {

    VaroBuilder states(Collection<State> state);

    Varo create();
}
