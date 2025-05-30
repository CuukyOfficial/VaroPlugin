package de.cuuky.varo.listener;

import java.math.BigDecimal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.config.language.Contexts;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.listener.helper.cancelable.CancelableType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelable;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;
import de.cuuky.varo.player.stats.stat.PlayerState;
import io.github.almightysatan.slams.Placeholder;

public class PlayerDeathListener implements Listener {

    private void kickDeadPlayer(VaroPlayer deadPlayer, VaroPlayer killerPlayer, String cause) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!deadPlayer.isOnline())
                    return;

                if (killerPlayer == null)
                    Messages.PLAYER_KICK_DEATH.kick(deadPlayer, new Contexts.DeathContext(deadPlayer, cause));
                else
                    Messages.PLAYER_KICK_KILL.kick(deadPlayer, new Contexts.KillContext(deadPlayer, killerPlayer, cause));
            }
        }.runTaskLater(Main.getInstance(), 1L);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        Player killerPlayer = event.getEntity().getKiller();
        VaroPlayer deadP = VaroPlayer.getPlayer(deadPlayer);
        VaroPlayer killer = killerPlayer == null ? null : VaroPlayer.getPlayer(killerPlayer);
        event.setDeathMessage(null);

        if (Main.getVaroGame().hasStarted()) {
        	deadP.onEvent(BukkitEventType.DEATH);
            if (killerPlayer != null)
            	killer.onEvent(BukkitEventType.KILL);
        	
            PlayerHit hit = PlayerHit.getHit(deadPlayer);
            if (hit != null)
                hit.over();

            String cause = deadPlayer.getLastDamageCause() != null ? deadPlayer.getLastDamageCause().getCause().toString() : "?";
            if (deadP.getTeam() == null || deadP.getTeam().getLives().compareTo(BigDecimal.ONE) >= 0) {
                if (killerPlayer == null) {
                    Messages.LOG_DEATH_ELIMINATED_OTHER.log(LogType.DEATH, new Contexts.DeathContext(deadP, cause));
                    Messages.PLAYER_DEATH_ELIMINATED_OTHER.broadcast(new Contexts.DeathContext(deadP, cause));
                } else {
                    Messages.LOG_DEATH_ELIMINATED_PLAYER.log(LogType.KILL, new Contexts.KillContext(deadP, killer, cause));
                    Messages.PLAYER_DEATH_ELIMINATED_PLAYER.broadcast(new Contexts.KillContext(deadP, killer, cause));
                }

                deadP.onEvent(BukkitEventType.DEATH_NO_LIVES);

                if (!ConfigSetting.PLAYER_SPECTATE_AFTER_DEATH.getValueAsBoolean()) {
                    if (ConfigSetting.KICK_DELAY_AFTER_DEATH.isIntActivated()) {
                        Messages.PLAYER_DISCONNECT_KICK_IN_SECONDS.broadcast(deadP, Placeholder.constant("kick-delay", String.valueOf(ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt())));
                        deadP.getStats().setState(PlayerState.SPECTATOR, ConfigSetting.PLAYER_SHOW_DEATH_SCREEN.getValueAsBoolean());
                        deadP.update();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                            	if (!deadP.getStats().isAlive()) {
	                                deadP.getStats().setState(PlayerState.DEAD);

	                                if (!deadP.isOnline())
	                                    return;

	                                kickDeadPlayer(deadP, killer, cause);
	                                Messages.PLAYER_DISCONNECT_KICK_DELAY_OVER.broadcast(deadP);
                            	}
                            }
                        }.runTaskLater(Main.getInstance(), ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt() * 20L);
                    } else
                        kickDeadPlayer(deadP, killer, cause);
                } else {
                    deadP.getStats().setState(PlayerState.SPECTATOR, ConfigSetting.PLAYER_SHOW_DEATH_SCREEN.getValueAsBoolean());
                    deadP.update();
                }
            } else {
                if (ConfigSetting.RESPAWN_PROTECTION.isIntActivated()) {
                    VaroCancelable prot = new VaroCancelable(CancelableType.PROTECTION, deadP, ConfigSetting.RESPAWN_PROTECTION.getValueAsInt());
                    Messages.PLAYER_DEATH_RESPAWN_PROTECTION.broadcast(deadP);
                    prot.setTimerHook(() -> Messages.PLAYER_DEATH_RESPAWN_PROTECTION_OVER.broadcast(deadP));
                }

                deadP.getTeam().setLives(deadP.getTeam().getLives().subtract(BigDecimal.ONE));
                if (killerPlayer == null) {
                    Messages.LOG_DEATH_LIFE_OTHER.log(LogType.DEATH, new Contexts.DeathContext(deadP, cause));
                    Messages.PLAYER_DEATH_LIFE_OTHER.broadcast(new Contexts.DeathContext(deadP, cause));
                } else {
                    Messages.LOG_DEATH_LIFE_PLAYER.log(LogType.KILL, new Contexts.KillContext(deadP, killer, cause));
                    Messages.PLAYER_DEATH_LIFE_PLAYER.broadcast(new Contexts.KillContext(deadP, killer, cause));
                }
            }
        }
    }
}