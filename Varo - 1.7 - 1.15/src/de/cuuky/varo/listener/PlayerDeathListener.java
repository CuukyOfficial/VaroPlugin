package de.cuuky.varo.listener;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.version.VersionUtils;

public class PlayerDeathListener implements Listener {

	private void checkHealth(Player killer) {
		int healthAdd = ConfigSetting.KILLER_ADD_HEALTH_ON_KILL.getValueAsInt();
		if(healthAdd > 0) {
			double hearts = VersionUtils.getHearts(killer) + healthAdd;
			if(hearts > 20.0)
				killer.setHealth(20.0);
			else
				killer.setHealth(hearts);
			killer.sendMessage(Main.getPrefix() + "§7Du hast durch den Kill an §4" + healthAdd / 2 + "§7 Herzen regeneriert bekommen!");
		}
	}

	private void kickDeadPlayer(VaroPlayer deadPlayer, VaroPlayer killerPlayer) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if(!deadPlayer.isOnline())
					return;

				if(killerPlayer == null)
					deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_DEAD.getValue(deadPlayer, deadPlayer));
				else
					deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_KILLED.getValue(deadPlayer, deadPlayer).replace("%killer%", killerPlayer.getName()));
			}
		}, 1);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player deadPlayer = event.getEntity();
		Player killerPlayer = event.getEntity().getKiller();
		VaroPlayer deadP = VaroPlayer.getPlayer(deadPlayer);
		VaroPlayer killer = killerPlayer == null ? null : VaroPlayer.getPlayer(killerPlayer);
		event.setDeathMessage(null);

		if(Main.getVaroGame().hasStarted()) {
			PlayerHit hit = PlayerHit.getHit(deadPlayer);
			if(hit != null)
				hit.over();

			deadPlayer.getWorld().strikeLightningEffect(deadPlayer.getLocation());
			for(ItemStack stack : Main.getDataManager().getListManager().getDeathItems().getItems())
				if(stack.getType() != Material.AIR)
					deadPlayer.getWorld().dropItemNaturally(deadPlayer.getLocation(), stack);

			for(int i = 0; i < 3; i++)
				deadPlayer.getWorld().playEffect(deadPlayer.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

			if(deadP.getTeam() == null || deadP.getTeam().getLifes() <= 1) {
				if(killerPlayer == null) {
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_DEATH.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", deadPlayer.getLastDamageCause().getCause().toString()));
					Bukkit.broadcastMessage(ConfigMessages.DEATH_DEAD.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%reason%", deadPlayer.getLastDamageCause().getCause().toString()));
				} else {
					PlayerHit hit1 = PlayerHit.getHit(killerPlayer);
					if(hit1 != null)
						hit1.over();

					if(killer.getTeam() != null && ConfigSetting.ADD_TEAM_LIFE_ON_KILL.isIntActivated()) {
						try {
							killer.getTeam().setLifes(killer.getTeam().getLifes() + ConfigSetting.ADD_TEAM_LIFE_ON_KILL.getValueAsDouble());
						} catch(Exception e) {
							killer.getTeam().setLifes(killer.getTeam().getLifes() + ConfigSetting.ADD_TEAM_LIFE_ON_KILL.getValueAsInt());
						}
						killer.sendMessage(ConfigMessages.DEATH_KILL_LIFE_ADD.getValue(killer, killer));
					}

					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.KILL, ConfigMessages.ALERT_DISCORD_KILL.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()));
					Bukkit.broadcastMessage(ConfigMessages.DEATH_KILLED_BY.getValue(null, deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()));

					killer.onEvent(BukkitEventType.KILL);
					checkHealth(killerPlayer);
				}
				
				deadP.onEvent(BukkitEventType.KILLED);

				if(!ConfigSetting.PLAYER_SPECTATE_AFTER_DEATH.getValueAsBoolean()) {
					if(ConfigSetting.KICK_DELAY_AFTER_DEATH.isIntActivated()) {
						Bukkit.broadcastMessage(ConfigMessages.QUIT_KICK_IN_SECONDS.getValue(null, deadP).replace("%countdown%", String.valueOf(ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt())));
						deadP.getStats().setState(PlayerState.SPECTATOR);
						deadP.setSpectacting();
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

							@Override
							public void run() {
								deadP.getStats().setState(PlayerState.DEAD);
								kickDeadPlayer(deadP, killer);
								Bukkit.broadcastMessage(ConfigMessages.QUIT_KICK_DELAY_OVER.getValue(null, deadP));
							}
						}, ConfigSetting.KICK_DELAY_AFTER_DEATH.getValueAsInt() * 20);
					} else
						kickDeadPlayer(deadP, killer);
				} else {
					deadP.setSpectacting();
					deadP.getStats().setState(PlayerState.SPECTATOR);
					deadP.update();
				}
			} else {
				if(ConfigSetting.RESPAWN_PROTECTION.isIntActivated()) {
					VaroCancelAble prot = new VaroCancelAble(CancelAbleType.PROTECTION, deadP, ConfigSetting.RESPAWN_PROTECTION.getValueAsInt());
					Bukkit.broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION.getValue(null, deadP).replace("%seconds%", String.valueOf(ConfigSetting.RESPAWN_PROTECTION.getValueAsInt())));
					prot.setTimerHook(new Runnable() {

						@Override
						public void run() {
							Bukkit.broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION_OVER.getValue(null, deadP));
						}
					});
				}

				deadP.getTeam().setLifes(deadP.getTeam().getLifes() - 1);
				Bukkit.broadcastMessage(ConfigMessages.DEATH_LIFE_LOST.getValue(null, deadP));
			}
		}
	}
}