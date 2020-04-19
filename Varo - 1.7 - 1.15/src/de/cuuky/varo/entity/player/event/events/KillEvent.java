package de.cuuky.varo.entity.player.event.events;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.cfw.version.types.Sounds;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;

public class KillEvent extends BukkitEvent {

	public KillEvent() {
		super(BukkitEventType.KILL);
	}

	@Override
	public void onExec(VaroPlayer player) {
		if (ConfigSetting.DEATH_SOUND.getValueAsBoolean())
			VersionUtils.getOnlinePlayer().forEach(pl -> pl.playSound(pl.getLocation(), Sounds.WITHER_IDLE.bukkitSound(), 1, 1));

		player.getStats().addKill();
		super.onExec(player);
	}

}
