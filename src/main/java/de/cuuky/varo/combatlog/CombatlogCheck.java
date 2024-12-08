package de.cuuky.varo.combatlog;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.PlayerState;
import de.cuuky.varo.player.stats.stat.Strike;

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
        if (!Main.getVaroGame().isRunning() || (hit = PlayerHit.getHit(player)) == null)
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
            Messages.LOG_STRIKE_COMBAT_LOG.log(LogType.ALERT, vp);
        } else
            Messages.LOG_COMBAT_LOG.log(LogType.ALERT, vp);

        Main.getLanguageManager().broadcastMessage(ConfigMessages.COMBAT_LOGGED_OUT, vp);
    }

    public boolean isCombatLog() {
        return combatLog;
    }
}