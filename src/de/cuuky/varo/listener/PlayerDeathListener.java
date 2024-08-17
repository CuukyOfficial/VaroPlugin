package de.cuuky.varo.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class PlayerDeathListener implements Listener {

    private void kickDeadPlayer(VaroPlayer deadPlayer, VaroPlayer killerPlayer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!deadPlayer.isOnline())
                    return;

                if (killerPlayer == null)
                    deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_DEAD.getValue(deadPlayer, deadPlayer));
                else
                    deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_KILLED.getValue(deadPlayer, deadPlayer).replace("%killer%", killerPlayer.getName()));
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
            if (deadP.getTeam() == null || deadP.getTeam().getLifes() - 2D < -0.00000001D) {
                if (killerPlayer == null) {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DEATH_ELIMINATED_OTHER.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", cause));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_ELIMINATED_OTHER, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", cause);
                } else {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.KILL, ConfigMessages.ALERT_DEATH_ELIMINATED_PLAYER.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()).replace("%killerkills%", String.valueOf(killer.getStats().getKills())));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_ELIMINATED_PLAYER, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()).replace("%killerkills%", String.valueOf(killer.getStats().getKills()));
                }

                deadP.onEvent(BukkitEventType.DEATH_NO_LIFES);

                if (!ConfigSetting.PLAYER_SPECTATE_AFTER_DEATH.getValueAsBoolean()) {
                    if (ConfigSetting.KICK_DELAY_AFTER_DEATH.isIntActivated()) {
                        Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_IN_SECONDS, deadP).replace("%countdown%", String.valueOf(ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt()));
                        deadP.getStats().setState(PlayerState.SPECTATOR, ConfigSetting.PLAYER_SHOW_DEATH_SCREEN.getValueAsBoolean());
                        deadP.update();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                            	if (!deadP.getStats().isAlive()) {
	                                deadP.getStats().setState(PlayerState.DEAD);
	                                kickDeadPlayer(deadP, killer);
	                                Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_DELAY_OVER, deadP);
                            	}
                            }
                        }.runTaskLater(Main.getInstance(), ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt() * 20L);
                    } else
                        kickDeadPlayer(deadP, killer);
                } else {
                    deadP.getStats().setState(PlayerState.SPECTATOR, ConfigSetting.PLAYER_SHOW_DEATH_SCREEN.getValueAsBoolean());
                    deadP.update();
                }
            } else {
                if (ConfigSetting.RESPAWN_PROTECTION.isIntActivated()) {
                    VaroCancelAble prot = new VaroCancelAble(CancelAbleType.PROTECTION, deadP, ConfigSetting.RESPAWN_PROTECTION.getValueAsInt());
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION, deadP).replace("%seconds%", String.valueOf(ConfigSetting.RESPAWN_PROTECTION.getValueAsInt()));
                    prot.setTimerHook(() -> Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION_OVER, deadP));
                }

                deadP.getTeam().setLifes(deadP.getTeam().getLifes() - 1);
                if (killerPlayer == null) {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DEATH_LIFE_OTHER.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", cause));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_LIFE_OTHER, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", cause);
                } else {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.KILL, ConfigMessages.ALERT_DEATH_LIFE_PLAYER.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()).replace("%killerkills%", String.valueOf(killer.getStats().getKills())));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_LIFE_PLAYER, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()).replace("%killerkills%", String.valueOf(killer.getStats().getKills()));
                }
            }
        }
    }
}