package de.varoplugin.varo.game.entity.player.session;

import java.util.Calendar;
import java.util.Objects;

public class EmptySessionBuilder implements SessionBuilder {

    private Calendar start;

    @Override
    public SessionBuilder start(Calendar calendar) {
        this.start = Objects.requireNonNull(calendar);
        return this;
    }

    @Override
    public Session build() {
        if (this.start == null) throw new IllegalArgumentException("Start may not be null");
        return new SessionImpl(this.start, null);
    }
}
