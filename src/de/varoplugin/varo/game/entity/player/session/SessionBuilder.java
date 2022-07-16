package de.varoplugin.varo.game.entity.player.session;

import java.util.Calendar;

public interface SessionBuilder {

    SessionBuilder start(Calendar calendar);

    Session build();

}
