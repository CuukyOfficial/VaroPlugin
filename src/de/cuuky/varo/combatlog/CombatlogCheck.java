package de.cuuky.varo.combatlog;

import de.cuuky.varo.app.Main;
import de.cuuky.varo.Alert;
import de.cuuky.varo.AlertType;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import org.bukkit.entity.Player;

public class CombatlogCheck {

    private final Player player;
    private final VaroPlayer vp;
    private PlayerHit hit;
    private boolean combatLog;

    public CombatlogCheck(Player player) {
        this.player = player;
        this.vp = VaroPlayer.getPlayer(player);
        this.combatLog = false;

        this.check();
    }

    private void check() {
        if (Main.getVaroGame().getGameState() != GameState.STARTED || (hit = PlayerHit.getHit(player)) == null)
            return;

        if (!vp.getStats().isAlive() || vp.isAdminIgnore())
            return;

        this.combatLog = true;
    }

    public void punish() {
        if (hit.getOpponent() != null && hit.getOpponent().isOnline() && PlayerHit.getHit(hit.getOpponent()) != null)
            PlayerHit.getHit(hit.getOpponent()).over();

        if (ConfigSetting.KILL_ON_COMBATLOG.getValueAsBoolean()) {
            this.player.setHealth(0);
            this.vp.getStats().setState(PlayerState.DEAD);
        }

        vp.onEvent(BukkitEventType.KICKED);
        new Alert(AlertType.COMBATLOG, vp.getName() + " hat sich im Kampf ausgeloggt!");
        if (ConfigSetting.STRIKE_ON_COMBATLOG.getValueAsBoolean()) {
            vp.getStats().addStrike(new Strike("CombatLog", vp, "CONSOLE"));
            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG_STRIKE.getValue(null, vp));
        } else
            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_COMBAT_LOG.getValue(null, vp));

        Main.getLanguageManager().broadcastMessage(ConfigMessages.COMBAT_LOGGED_OUT, vp);
    }

    public boolean isCombatLog() {
        return combatLog;
    }
}