package de.cuuky.varo.ban;

import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.varoplugin.banapi.Ban;
import de.varoplugin.banapi.BanApi;
import de.varoplugin.banapi.BanDuration;
import de.varoplugin.banapi.LatestBansHandler;
import de.varoplugin.banapi.LatestBansHandler.Mode;
import de.varoplugin.banapi.User;
import de.varoplugin.banapi.request.RequestFailedException;

public class VaroPlayerBanHandler {

	private final BanApi banApi;
	private final LatestBansHandler bansHandler;

	public VaroPlayerBanHandler() {
		banApi = new BanApi("http://varoplugin.de/banapi/", null, new Consumer<RequestFailedException>() {

			@Override
			public void accept(RequestFailedException t) {
				t.printStackTrace();
			}
		});

		this.bansHandler = new LatestBansHandler(banApi, Mode.MINECRAFT_ONLY, new Consumer<Throwable>() {

			@Override
			public void accept(Throwable t) {
				t.printStackTrace();
			}
		});

		this.bansHandler.registerListener(new VaroPlayerBanListener(this));
		this.bansHandler.start();
	}

	private String getDuration(long duration) {
		if (duration == -1)
			return "Permanent";
		if (duration == 0)
			return "Unban";
		else
			return BanDuration.getDisplaynameFromMillis(duration);
	}

	public boolean hasBan(Player player, PlayerLoginEvent event) {
		if (this.bansHandler.getCurrentData() == null)
			return false;

		User user = this.bansHandler.getCurrentData().getUser(player.getUniqueId());
		if (user == null || !user.hasActiveMinecraftBan())
			return false;

		Ban ban = user.getLatestMinecraftBan();
		if (event == null)
			player.kickPlayer(getKickMessage(ban));
		else
			event.disallow(Result.KICK_BANNED, getKickMessage(ban));
		return true;
	}

	public String getKickMessage(Ban ban) {
		return "§4You have been banned from all Varo-Servers!\n§cReason: §f" + ban.getReason() + "\n§cDuration: §f" + this.getDuration(ban.getDuration()) + "\n§cDescription: §f" + (ban.getNotes() == null ? "/" : ban.getNotes()) + "\n§cUnban here: §fhttps://discord.gg/CnDSVVx";
	}
}