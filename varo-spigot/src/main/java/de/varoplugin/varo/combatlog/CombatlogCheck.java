package de.varoplugin.varo.combatlog;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.event.BukkitEventType;
import de.varoplugin.varo.player.stats.stat.PlayerState;
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
            vp.getStats().strike("CombatLog", "CONSOLE");
            Messages.LOG_STRIKE_COMBAT_LOG.log(LogType.ALERT, vp);
        } else
            Messages.LOG_COMBAT_LOG.log(LogType.ALERT, vp);

        Messages.PLAYER_COMBAT_LOGGED_OUT.broadcast(this.vp);
    }

    public boolean isCombatLog() {
        return combatLog;
    }
}