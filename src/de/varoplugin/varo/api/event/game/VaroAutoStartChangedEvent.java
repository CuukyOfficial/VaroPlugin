package de.varoplugin.varo.api.event.game;

import de.varoplugin.varo.game.Varo;

import java.util.Calendar;

public class VaroAutoStartChangedEvent extends VaroGameCancelableEvent {

    private final Calendar newAutoStart;

    public VaroAutoStartChangedEvent(Varo varo, Calendar newAutoStart) {
        super(varo);
        this.newAutoStart = newAutoStart;
    }

    public Calendar getNewAutoStart() {
        return this.newAutoStart;
    }
}
