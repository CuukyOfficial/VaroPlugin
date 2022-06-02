package de.varoplugin.varo.game.player.login;

import de.varoplugin.varo.game.player.VaroPlayer;

import java.util.function.Function;

public class AlivePlayerCheck implements Function<VaroPlayer, VaroLoginResult> {

    // TODO: Check
    @Override
    public DefaultLoginResult apply(VaroPlayer varoPlayer) {
        return DefaultLoginResult.ALLOWED;
    }
}