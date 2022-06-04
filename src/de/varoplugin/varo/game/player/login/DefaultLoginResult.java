package de.varoplugin.varo.game.player.login;

import de.varoplugin.varo.game.Varo;
import de.varoplugin.varo.game.player.VaroPlayer;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public enum DefaultLoginResult implements VaroLoginResult {

    ALLOWED((varo, event, player) -> varo.register(player)),
    // Not ideal solution because of ui seperation
    PLAYER_DEAD(new LoginKick("You died!", (v, vp) -> new Object[0]));

    private final ResultApplier resultApplier;

    DefaultLoginResult(ResultApplier resultApplier) {
        this.resultApplier = resultApplier;
    }

    @Override
    public void apply(Varo varo, PlayerLoginEvent event, VaroPlayer player) {
        this.resultApplier.apply(varo, event, player);
    }
}