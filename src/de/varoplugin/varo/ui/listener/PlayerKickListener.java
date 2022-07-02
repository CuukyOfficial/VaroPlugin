package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroGameLoginEvent;
import de.varoplugin.varo.api.event.game.player.VaroPlayerLoginEvent;
import de.varoplugin.varo.game.GameKickResult;
import de.varoplugin.varo.game.VaroKickResult;
import de.varoplugin.varo.game.entity.player.GamePlayerKickResult;
import org.bukkit.event.EventHandler;

import java.util.Arrays;

public class PlayerKickListener extends UiListener {

    private enum KickMessages {

        // TODO: Use language system
        NOT_A_PARTICIPANT(GameKickResult.NOT_A_PARTICIPANT, "You are not registered in this Varo!");

        private final VaroKickResult result;
        private final String message;

        KickMessages(VaroKickResult result, String message) {
            this.result = result;
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public static KickMessages getMessage(VaroKickResult result) {
            return Arrays.stream(values()).filter(m -> m.result.equals(result)).findAny().orElse(null);
        }
    }

    @EventHandler
    public void onVaroPlayerKick(VaroPlayerLoginEvent event) {
        if (event.getResult() == GamePlayerKickResult.PLAYER_DEAD) {
            event.getSource().setKickMessage("You are dead!");
        }
     }

    @EventHandler
    public void onGamePlayerKick(VaroGameLoginEvent event) {
        KickMessages message = KickMessages.getMessage(event.getResult());
        if (message == null) return;
        event.getSource().setKickMessage(message.getMessage());
    }
}