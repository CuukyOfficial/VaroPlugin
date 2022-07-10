package de.varoplugin.varo.game.entity.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface VaroPlayerFactory {

    VaroPlayerFactory uuid(UUID uuid);

    VaroPlayerFactory name(String name);

    VaroPlayerFactory player(Player player);

    VaroPlayerFactory state(VaroParticipantState state);

    VaroPlayerFactory mode(VaroPlayerMode mode);

    VaroPlayerFactory parseMissing() throws ParseException;

    /**
     * May take some time because of Mojang server request.
     * @return New @{@link VaroPlayer}
     */
    VaroPlayer create();

}
