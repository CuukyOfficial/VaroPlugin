package de.varoplugin.varo.game.entity.player;

import java.util.UUID;

public interface PlayerBuilder {

    PlayerBuilder uuid(UUID uuid);

    PlayerBuilder name(String name);

    PlayerBuilder player(org.bukkit.entity.Player player);

    PlayerBuilder state(ParticipantState state);

    PlayerBuilder mode(PlayerMode mode);

    /**
     * May take some time because of Mojang server request.
     */
    PlayerBuilder parseMissing() throws ParseException;

    VaroPlayer create();

}
