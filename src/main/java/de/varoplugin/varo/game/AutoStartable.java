package de.varoplugin.varo.game;

import java.util.Calendar;

public interface AutoStartable {

    boolean setAutoStart(Calendar calendar);

    Calendar getAutoStart();

}
