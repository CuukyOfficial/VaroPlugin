package de.cuuky.varo.combatlog;

import org.bukkit.event.player.PlayerQuitEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.stats.stat.PlayerState;

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
		if(Main.getGame().getGameState() == GameState.END) {
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
