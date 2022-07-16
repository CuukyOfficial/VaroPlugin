package de.varoplugin.varo.game.strike;

import java.util.UUID;

public interface StrikeBuilder {

    StrikeBuilder uuid(UUID uuid);

    StrikeBuilder type(StrikeType type);

    Strike create();
}
