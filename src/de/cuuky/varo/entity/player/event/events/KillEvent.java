package de.cuuky.varo.entity.player.event.events;

import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.Stats;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;

public class KillEvent extends BukkitEvent {

	public KillEvent() {
		super(BukkitEventType.KILL);
	}

	@Override
	public void onExec(VaroPlayer player) {
		Stats stats = player.getStats();
		PlayerHit hit1 = PlayerHit.getHit(player.getPlayer());
		if (hit1 != null)
			hit1.over();
		
		this.checkHealth(player.getPlayer());

		if (player.getTeam() != null) {
			Number addNumber = (Number) ConfigSetting.ADD_TEAM_LIFE_ON_KILL.getValue(), maxNumber = (Number) ConfigSetting.MAX_TEAM_LIFES.getValue();
			double add = addNumber.doubleValue(), max = maxNumber.doubleValue();

			if (add > 0 && player.getTeam().getLifes() + add <= max) {
				player.getTeam().setLifes(player.getTeam().getLifes() + add);
				player.sendMessage(ConfigMessages.DEATH_KILL_LIFE_ADD);
			}
		}
		
		if (ConfigSetting.PLAY_TIME.isIntActivated() && stats.getCountdown() >= 0 && !VaroEvent.getEvent(VaroEventType.MASS_RECORDING).isEnabled()) {
			// Adding time during a mass recording may or may not break something
			int timeAdded = 0;
			if (ConfigSetting.DEATH_TIME_ADD.isIntActivated())
				timeAdded = ConfigSetting.DEATH_TIME_ADD.getValueAsInt();

			if (ConfigSetting.DEATH_TIME_MIN.isIntActivated() && stats.getCountdown() + timeAdded < ConfigSetting.DEATH_TIME_MIN.getValueAsInt())
				timeAdded = ConfigSetting.DEATH_TIME_MIN.getValueAsInt() - stats.getCountdown();

			if (timeAdded > 0) {
				stats.setCountdown(timeAdded + stats.getCountdown());
				player.sendMessage(Main.getPrefix() + ConfigMessages.DEATH_KILL_TIME_ADD.getValue(player).replace("%timeAdded%", String.valueOf(timeAdded)));
			}
		}

		stats.addKill();
		super.onExec(player);
	}

	private void checkHealth(Player killer) {
		int healthAdd = ConfigSetting.KILLER_ADD_HEALTH_ON_KILL.getValueAsInt();
		if (healthAdd > 0) {
			double hearts = killer.getHealth() + healthAdd;
			killer.setHealth(Math.min(hearts, killer.getMaxHealth()));
			killer.sendMessage(Main.getPrefix() + "§7Du hast durch den Kill an §4" + healthAdd / 2 + "§7 Herzen regeneriert bekommen!");
		}
	}
}