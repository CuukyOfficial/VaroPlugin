package de.cuuky.varo.combatlog;

import de.cuuky.varo.game.Game;
import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.PlayerState;
import de.cuuky.varo.game.state.GameState;

public class CombatlogCheck {

	/*
	 * OLD CODE
	 */

	private boolean combatLog;

	public CombatlogCheck(PlayerQuitEvent event) {
		combatLog = true;

		check(event);
	}

	private void check(PlayerQuitEvent event) {
		if(Game.getInstance().getGameState() == GameState.END) {
			this.combatLog = false;
			return;
		}

		if(Hit.getHit(event.getPlayer()) == null) {
			this.combatLog = false;
			return;
		}

		VaroPlayer vp = VaroPlayer.getPlayer(event.getPlayer().getName());
		Hit hit = Hit.getHit(event.getPlayer());

		if(hit.getOpponent() != null && hit.getOpponent().isOnline())
			Hit.getHit(hit.getOpponent()).over();

		if(!vp.getStats().isAlive()) {
			this.combatLog = false;
			return;
		}

		if(ConfigEntry.KILL_ON_COMBATLOG.getValueAsBoolean()) {
			event.getPlayer().setHealth(0);
			vp.getStats().setState(PlayerState.DEAD);
		}

		new Combatlog(vp);
	}

	public boolean isCombatLog() {
		return combatLog;
	}
}
