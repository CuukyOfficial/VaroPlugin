package de.varoplugin.varo.game.strike;

import java.util.UUID;

public interface StrikeFactory {

    StrikeFactory uuid(UUID uuid);

    StrikeFactory type(StrikeType type);

    Strike create();
}
