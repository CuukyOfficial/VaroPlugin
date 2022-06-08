package de.varoplugin.varo.game.tasks;

import de.varoplugin.varo.game.Varo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
public class LobbyLoginTask extends VaroTask {

    public LobbyLoginTask(Varo varo) {
        super(varo);
    }

    /**
     * https://www.google.de/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F467811480042142462%2F&psig=AOvVaw0Rauu08g3t5ZWSN9A2-gai&ust=1654608964950000&source=images&cd=vfe&ved=0CAwQjRxqFwoTCKC05e_4mPgCFQAAAAAdAAAAABAI
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onLobbyLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED)
            return;

        this.varo.register(event.getPlayer());
    }
}