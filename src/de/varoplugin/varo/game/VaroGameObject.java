package de.varoplugin.varo.game;

import java.util.UUID;

public interface VaroGameObject {

    void initialize(Varo varo);

    Varo getVaro();

    UUID getUuid();

}