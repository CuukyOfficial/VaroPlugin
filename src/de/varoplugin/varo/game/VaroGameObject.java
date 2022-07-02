package de.varoplugin.varo.game;

import de.varoplugin.varo.util.map.UniqueObject;

public interface VaroGameObject extends UniqueObject {

    void initialize(Varo varo);

    Varo getVaro();

}