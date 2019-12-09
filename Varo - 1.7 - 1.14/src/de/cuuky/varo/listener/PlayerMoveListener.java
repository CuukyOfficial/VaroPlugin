package de.cuuky.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();
		Player player = event.getPlayer();
		VaroPlayer vp = VaroPlayer.getPlayer(player);

		if(from.getX() == to.getX() && from.getZ() == to.getZ())
			return;

		if(VaroCancelAble.getCancelAble(vp, CancelAbleType.FREEZE) != null || Game.getInstance().isStarting() && !vp.getStats().isSpectator()) {
			event.setTo(from);
			return;
		}

		if(Game.getInstance().getGameState() == GameState.LOBBY) {
			if(ConfigEntry.CAN_MOVE_BEFORE_START.getValueAsBoolean() || player.isOp() || player.getGameMode() == GameMode.CREATIVE)
				return;

			event.setTo(from);
			player.sendMessage(ConfigMessages.PROTECTION_NO_MOVE_START.getValue());
			return;
		} else if(Game.getInstance().getGameState() == GameState.STARTED) {
			if(Game.getInstance().isStarting() || vp.getStats().isSpectator() || ConfigEntry.CANWALK_PROTECTIONTIME.getValueAsBoolean() || !ConfigEntry.JOIN_PROTECTIONTIME.isIntActivated() || Game.getInstance().isFirstTime() || vp.isAdminIgnore()) {
				return;
			}

			if(vp.isInProtection()) {
				event.setTo(from);
				player.sendMessage(ConfigMessages.JOIN_NO_MOVE_IN_PROTECTION.getValue());
				return;
			}
		}
	}
}
