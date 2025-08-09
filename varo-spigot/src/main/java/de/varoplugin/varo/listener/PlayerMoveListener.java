package de.varoplugin.varo.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.listener.helper.cancelable.CancelableType;
import de.varoplugin.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.varo.player.VaroPlayer;

public class PlayerMoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Location from = event.getFrom();
		Location to = event.getTo();

		if (from.getX() == to.getX() && from.getZ() == to.getZ())
			return;

		Player player = event.getPlayer();
		VaroPlayer vp = VaroPlayer.getPlayer(player);

		if (VaroCancelable.getCancelable(vp, CancelableType.FREEZE) != null || Main.getVaroGame().isStarting() && !vp.getStats().isSpectator()) {
			event.setTo(from);
			return;
		}

		if (!Main.getVaroGame().hasStarted()) {
			if (ConfigSetting.CAN_MOVE_BEFORE_START.getValueAsBoolean() || player.isOp() || player.getGameMode() == GameMode.CREATIVE)
				return;

			event.setTo(from);
			Messages.GAME_LOBBY_MOVE.send(vp);
			return;
		} else if (Main.getVaroGame().isRunning()) {
			if (vp.getStats().isSpectator() || ConfigSetting.CANWALK_PROTECTIONTIME.getValueAsBoolean() || !ConfigSetting.JOIN_PROTECTIONTIME.isIntActivated() || vp.isAdminIgnore())
				return;

			if (vp.isInProtection()) {
				event.setTo(from);
				Messages.PLAYER_MOVE_PROTECTION.send(vp);
				return;
			}
		}
	}
}
