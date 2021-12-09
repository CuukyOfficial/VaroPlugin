package de.cuuky.varo.entity.player.event.events;

import org.bukkit.Sound;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

public class KillEvent extends BukkitEvent {
	
	private Sound deathSound;

	public KillEvent() {
		super(BukkitEventType.KILL);
		this.deathSound = Sound.valueOf(ConfigSetting.DEATH_SOUND.getValueAsString());
	}

	@Override
	public void onExec(VaroPlayer player) {
		PlayerHit hit1 = PlayerHit.getHit(player.getPlayer());
		if (hit1 != null)
			hit1.over();

		if (player.getTeam() != null) {
			Number addNumber = (Number) ConfigSetting.ADD_TEAM_LIFE_ON_KILL.getValue(), maxNumber = (Number) ConfigSetting.MAX_TEAM_LIFES.getValue();
			double add = addNumber.doubleValue(), max = maxNumber.doubleValue();

			if (add > 0 && player.getTeam().getLifes() + add <= max) {
				player.getTeam().setLifes(player.getTeam().getLifes() + add);
				player.sendMessage(ConfigMessages.DEATH_KILL_LIFE_ADD);
			}
		}

		if (ConfigSetting.DEATH_SOUND_ENABLED.getValueAsBoolean())
			VersionUtils.getVersionAdapter().getOnlinePlayers().forEach(pl -> pl.playSound(pl.getLocation(), this.deathSound, 1, 1));

		player.getStats().addKill();
		super.onExec(player);
	}
}