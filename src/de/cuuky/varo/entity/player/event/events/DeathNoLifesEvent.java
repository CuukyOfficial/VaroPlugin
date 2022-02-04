package de.cuuky.varo.entity.player.event.events;

import java.util.Date;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.event.BukkitEvent;
import de.cuuky.varo.entity.player.event.BukkitEventType;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.game.world.border.decrease.DecreaseReason;

public class DeathNoLifesEvent extends BukkitEvent {

	public DeathNoLifesEvent() {
		super(BukkitEventType.DEATH_NO_LIFES);
	}

	@Override
	public void onExec(VaroPlayer player) {
		player.getStats().removeCountdown();
		player.getStats().setDiedAt(new Date());
		player.getStats().setState(PlayerState.DEAD);

		if (Main.getVaroGame().getGameState() == GameState.STARTED)
			Main.getVaroGame().getVaroWorldHandler().decreaseBorder(DecreaseReason.DEATH);
	}
}