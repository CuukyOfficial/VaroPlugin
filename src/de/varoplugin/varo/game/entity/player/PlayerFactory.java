package de.varoplugin.varo.game.entity.player;

import java.util.UUID;

public interface PlayerFactory {

    PlayerFactory uuid(UUID uuid);

    PlayerFactory name(String name);

    PlayerFactory player(org.bukkit.entity.Player player);

    PlayerFactory state(ParticipantState state);

    PlayerFactory mode(PlayerMode mode);

    PlayerFactory parseMissing() throws ParseException;

    /**
     * May take some time because of Mojang server request.
     * @return New @{@link VaroPlayer}
     */
    VaroPlayer create();

}
