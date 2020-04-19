package de.cuuky.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();

		if (from.getX() == to.getX() && from.getZ() == to.getZ())
			return;

		Player player = event.getPlayer();
		VaroPlayer vp = VaroPlayer.getPlayer(player);

		if (VaroCancelAble.getCancelAble(vp, CancelAbleType.FREEZE) != null || Main.getVaroGame().isStarting() && !vp.getStats().isSpectator()) {
			event.setTo(from);
			return;
		}

		if (Main.getVaroGame().getGameState() == GameState.LOBBY) {
			if (ConfigSetting.CAN_MOVE_BEFORE_START.getValueAsBoolean() || player.isOp() || player.getGameMode() == GameMode.CREATIVE)
				return;

			event.setTo(from);
			player.sendMessage(ConfigMessages.PROTECTION_NO_MOVE_START.getValue(vp, vp));
			return;
		} else if (Main.getVaroGame().getGameState() == GameState.STARTED) {
			if (vp.getStats().isSpectator() || ConfigSetting.CANWALK_PROTECTIONTIME.getValueAsBoolean() || !ConfigSetting.JOIN_PROTECTIONTIME.isIntActivated() || vp.isAdminIgnore())
				return;

			if (vp.isInProtection()) {
				event.setTo(from);
				player.sendMessage(ConfigMessages.JOIN_NO_MOVE_IN_PROTECTION.getValue(vp, vp));
				return;
			}
		}
	}
}
