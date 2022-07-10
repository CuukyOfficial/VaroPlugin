package de.varoplugin.varo.game;

import de.varoplugin.varo.util.map.UniqueObject;

public interface VaroObject extends UniqueObject {

    void initialize(Varo varo);

    Varo getVaro();

}