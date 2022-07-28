package de.varoplugin.varo.game.entity.player.session;

import java.util.Calendar;

public class SessionImpl implements Session {

    private final Calendar start;
    private final Calendar end;

    SessionImpl(Calendar start, Calendar end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Calendar getStart() {
        return this.start;
    }

    @Override
    public Calendar getFinish() {
        return this.end;
    }
}
