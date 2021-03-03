package de.cuuky.varo.entity.player.event.events;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.combatlog.PlayerHit;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

public class KillEvent extends BukkitEvent {

	public KillEvent() {
		super(BukkitEventType.KILL);
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

		if (ConfigSetting.DEATH_SOUND.getValueAsBoolean())
			VersionUtils.getOnlinePlayer().forEach(pl -> pl.playSound(pl.getLocation(), Sounds.WITHER_IDLE.bukkitSound(), 1, 1));

		player.getStats().addKill();
		super.onExec(player);
	}
}