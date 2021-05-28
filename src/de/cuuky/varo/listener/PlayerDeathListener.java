package de.cuuky.varo.listener;

import de.cuuky.cfw.version.VersionUtils;
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
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeathListener implements Listener {

    private void checkHealth(Player killer) {
        int healthAdd = ConfigSetting.KILLER_ADD_HEALTH_ON_KILL.getValueAsInt();
        if (healthAdd > 0) {
            double hearts = VersionUtils.getHearts(killer) + healthAdd;
            killer.setHealth(Math.min(hearts, 20.0));
            killer.sendMessage(Main.getPrefix() + "§7Du hast durch den Kill an §4" + healthAdd / 2 + "§7 Herzen regeneriert bekommen!");
        }
    }

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
            PlayerHit hit = PlayerHit.getHit(deadPlayer);
            if (hit != null)
                hit.over();

            if (ConfigSetting.DEATH_LIGHTNING_EFFECT.getValueAsBoolean())
                deadPlayer.getWorld().strikeLightningEffect(deadPlayer.getLocation());
            for (ItemStack stack : Main.getDataManager().getListManager().getDeathItems().getItems())
                if (stack.getType() != Material.AIR)
                    deadPlayer.getWorld().dropItemNaturally(deadPlayer.getLocation(), stack);

            for (int i = 0; i < 3; i++)
                deadPlayer.getWorld().playEffect(deadPlayer.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

            if (deadP.getTeam() == null || deadP.getTeam().getLifes() <= 1) {
                String cause = deadPlayer.getLastDamageCause() != null ? deadPlayer.getLastDamageCause().getCause().toString() : "?";
                if (killerPlayer == null) {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_DEATH.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", cause));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_DEAD, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", cause);
                } else {
                    Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.KILL, ConfigMessages.ALERT_DISCORD_KILL.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()));
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_KILLED_BY, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName());

                    killer.onEvent(BukkitEventType.KILL);
                    checkHealth(killerPlayer);
                }

                deadP.onEvent(BukkitEventType.KILLED);

                if (!ConfigSetting.PLAYER_SPECTATE_AFTER_DEATH.getValueAsBoolean()) {
                    if (ConfigSetting.KICK_DELAY_AFTER_DEATH.isIntActivated()) {
                        Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_IN_SECONDS, deadP).replace("%countdown%", String.valueOf(ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt()));
                        deadP.getStats().setState(PlayerState.SPECTATOR);
                        deadP.setSpectacting();
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                deadP.getStats().setState(PlayerState.DEAD);
                                kickDeadPlayer(deadP, killer);
                                Main.getLanguageManager().broadcastMessage(ConfigMessages.QUIT_KICK_DELAY_OVER, deadP);
                            }
                        }.runTaskLater(Main.getInstance(), ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt() * 20L);
                    } else
                        kickDeadPlayer(deadP, killer);
                } else {
                    deadP.setSpectacting();
                    deadP.getStats().setState(PlayerState.SPECTATOR);
                    deadP.update();
                }
            } else {
                if (ConfigSetting.RESPAWN_PROTECTION.isIntActivated()) {
                    VaroCancelAble prot = new VaroCancelAble(CancelAbleType.PROTECTION, deadP, ConfigSetting.RESPAWN_PROTECTION.getValueAsInt());
                    Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION, deadP).replace("%seconds%", String.valueOf(ConfigSetting.RESPAWN_PROTECTION.getValueAsInt()));
                    prot.setTimerHook(() -> Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION_OVER, deadP));
                }

                if (killerPlayer != null) {
                    killer.onEvent(BukkitEventType.KILL);
                    checkHealth(killerPlayer);
                }

                deadP.getTeam().setLifes(deadP.getTeam().getLifes() - 1);
                Main.getLanguageManager().broadcastMessage(ConfigMessages.DEATH_LIFE_LOST, deadP);
            }
        }
    }
}