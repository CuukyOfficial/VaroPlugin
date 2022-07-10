package de.varoplugin.varo.game;

import java.util.Objects;

public class EmptyVaroBuilder implements VaroBuilder {

    private State state = VaroState.LOBBY;

    @Override
    public VaroBuilder state(State state) {
        this.state = Objects.requireNonNull(state);
        return this;
    }

    @Override
    public Varo create() {
        return new VaroImpl(this.state);
    }
}
