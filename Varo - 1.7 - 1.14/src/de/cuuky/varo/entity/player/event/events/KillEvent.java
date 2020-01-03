package de.cuuky.varo.entity.player.event.events;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Sounds;

public class KillEvent extends BukkitEvent {

	public KillEvent() {
		super(BukkitEventType.KILL);
	}

	@Override
	public void onExec(VaroPlayer player) {
		if(ConfigEntry.DEATH_SOUND.getValueAsBoolean())
			VersionUtils.getOnlinePlayer().forEach(pl -> pl.playSound(pl.getLocation(), Sounds.WITHER_IDLE.bukkitSound(), 1, 1));

		player.getStats().addKill();
		super.onExec(player);
	}

}
