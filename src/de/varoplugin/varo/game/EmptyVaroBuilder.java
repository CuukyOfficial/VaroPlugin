package de.varoplugin.varo.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class EmptyVaroBuilder implements VaroBuilder {

    private final Collection<State> states;

    public EmptyVaroBuilder() {
        this.states = new HashSet<>();
    }

    @Override
    public VaroBuilder states(Collection<State> states) {
        this.states.addAll(Objects.requireNonNull(states));
        return this;
    }

    @Override
    public Varo create() {
        return new VaroImpl(this.states);
    }
}
