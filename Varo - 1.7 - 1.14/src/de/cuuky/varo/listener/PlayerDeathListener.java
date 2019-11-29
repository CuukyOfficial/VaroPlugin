package de.cuuky.varo.listener;

import de.cuuky.varo.data.DataManager;
import de.cuuky.varo.logger.LoggerMaster;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.Hit;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.logger.logger.EventLogger.LogType;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player deadPlayer = event.getEntity();
		Player killerPlayer = event.getEntity().getKiller();
		VaroPlayer deadP = VaroPlayer.getPlayer(deadPlayer);
		VaroPlayer killer = killerPlayer == null ? null : VaroPlayer.getPlayer(killerPlayer);
		event.setDeathMessage(null);

		if(Main.getGame().hasStarted()) {
			Hit hit = Hit.getHit(deadPlayer);
			if(hit != null)
				hit.over();

			deadPlayer.getWorld().strikeLightningEffect(deadPlayer.getLocation());
			for(ItemStack stack : DataManager.getInstance().getItemHandler().getDeathItems().getItems())
				if(stack.getType() != Material.AIR)
					deadPlayer.getWorld().dropItemNaturally(deadPlayer.getLocation(), stack);

			deadPlayer.getWorld().playEffect(deadPlayer.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			deadPlayer.getWorld().playEffect(deadPlayer.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
			deadPlayer.getWorld().playEffect(deadPlayer.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);

			if(deadP.getTeam() == null || deadP.getTeam().getLifes() <= 1) {
				deadP.onEvent(BukkitEventType.KILLED);

				if(!ConfigEntry.PLAYER_SPECTATE_AFTER_DEATH.getValueAsBoolean()) {
					if(ConfigEntry.KICK_DELAY_AFTER_DEATH.isIntActivated()) {
						Bukkit.broadcastMessage(ConfigMessages.KICK_IN_SECONDS.getValue(deadP).replace("%countdown%", String.valueOf(ConfigEntry.KICK_DELAY_AFTER_DEATH.getValueAsInt())));
						deadP.getStats().setState(PlayerState.SPECTATOR);
						deadP.setSpectacting();
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

							@Override
							public void run() {
								deadP.getStats().setState(PlayerState.DEAD);

								try {
									kickDeadPlayer(deadP, killer);
								} catch(NullPointerException e) {
									return;
								}

								Bukkit.broadcastMessage(ConfigMessages.KICK_DELAY_OVER.getValue(deadP));
							}
						}, ConfigEntry.KICK_DELAY_AFTER_DEATH.getValueAsInt() * 20);
					} else
						kickDeadPlayer(deadP, killer);
				} else {
					deadP.setSpectacting();
					deadP.getStats().setState(PlayerState.SPECTATOR);;
					deadP.update();
				}
			} else {
				if(ConfigEntry.RESPAWN_PROTECTION.isIntActivated()) {
					VaroCancelAble prot = new VaroCancelAble(CancelAbleType.PROTECTION, deadP, ConfigEntry.RESPAWN_PROTECTION.getValueAsInt());
					Bukkit.broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION.getValue(deadP).replace("%seconds%", String.valueOf(ConfigEntry.RESPAWN_PROTECTION.getValueAsInt())));
					prot.setTimerHook(new Runnable() {

						@Override
						public void run() {
							Bukkit.broadcastMessage(ConfigMessages.DEATH_RESPAWN_PROTECTION_OVER.getValue(deadP));
						}
					});
				}

				deadP.getTeam().setLifes(deadP.getTeam().getLifes() - 1);
				Bukkit.broadcastMessage(ConfigMessages.DEATH_LIFE_LOST.getValue(deadP));
			}

			if(killerPlayer == null) {
				LoggerMaster.getInstance().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_DEATH.getValue(deadP).replace("%death%", deadPlayer.getName()));
				Bukkit.broadcastMessage(ConfigMessages.DEATH_DEAD.getValue(deadP).replace("%death%", deadPlayer.getName()));
			} else {
				Hit hit1 = Hit.getHit(killerPlayer);
				if(hit1 != null)
					hit1.over();

				if(killer.getTeam() != null && ConfigEntry.ADD_TEAM_LIFE_ON_KILL.isIntActivated()) {
					try {
						killer.getTeam().setLifes(killer.getTeam().getLifes() + ConfigEntry.ADD_TEAM_LIFE_ON_KILL.getValueAsDouble());
					} catch(Exception e) {
						killer.getTeam().setLifes(killer.getTeam().getLifes() + ConfigEntry.ADD_TEAM_LIFE_ON_KILL.getValueAsInt());
					}
					killer.sendMessage(ConfigMessages.KILL_LIFE_ADD.getValue());
				}

				LoggerMaster.getInstance().getEventLogger().println(LogType.DEATH, ConfigMessages.ALERT_DISCORD_KILL.getValue(deadP).replace("%death%", deadPlayer.getName()).replace("%killer%", killerPlayer.getName()));
				Bukkit.broadcastMessage(ConfigMessages.DEATH_KILLED_BY.getValue(deadP).replaceAll("%death%", deadPlayer.getName()).replaceAll("%killer%", killerPlayer.getName()));

				killer.onEvent(BukkitEventType.KILL);
				checkHealth(killerPlayer);
			}
		}
	}

	private void kickDeadPlayer(VaroPlayer deadPlayer, VaroPlayer killerPlayer) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				if(killerPlayer == null)
					deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_DEAD.getValue(deadPlayer));
				else
					deadPlayer.getPlayer().kickPlayer(ConfigMessages.DEATH_KICK_KILLED.getValue(deadPlayer).replace("%killer%", killerPlayer.getName()));
			}
		}, 1);
	}

	private void checkHealth(Player killer) {
		int healthAdd = ConfigEntry.KILLER_ADD_HEALTH_ON_KILL.getValueAsInt();
		if(healthAdd > 0) {
			double hearts = killer.getHealth() + healthAdd;
			if(hearts > 20.0)
				killer.setHealth(20.0);
			else
				killer.setHealth(hearts);
			killer.sendMessage(Main.getPrefix() + "§7Du hast durch den Kill an §4" + healthAdd / 2 + "§7 Herzen regeneriert bekommen!");
		}
	}
}