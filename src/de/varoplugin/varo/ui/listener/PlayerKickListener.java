package de.varoplugin.varo.ui.listener;

import de.varoplugin.varo.api.event.game.VaroGameLoginKickEvent;
import de.varoplugin.varo.game.KickResult;
import de.varoplugin.varo.game.VaroKickResult;
import org.bukkit.event.EventHandler;

import java.util.Arrays;

public class PlayerKickListener extends UiListener {

    private enum KickMessages {

        // TODO: Use language system
        NOT_A_PARTICIPANT(KickResult.NOT_A_PARTICIPANT, "You are not registered in this Varo!");

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
    public void onGamePlayerKick(VaroGameLoginKickEvent event) {
        KickMessages message = KickMessages.getMessage(event.getResult());
        if (message == null) return;
        event.getLoginEvent().setKickMessage(message.getMessage());
    }
}