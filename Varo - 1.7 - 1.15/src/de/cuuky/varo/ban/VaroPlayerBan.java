package de.cuuky.varo.ban;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroPlayerBan {

	private static ArrayList<VaroPlayerBan> bans;

	static {
		bans = new ArrayList<>();
	}

	private String uuid, description;
	private VaroPlayerBanReason reason;

	public VaroPlayerBan(String uuid, VaroPlayerBanReason reason, String description) {
		this.uuid = uuid;
		this.reason = reason;
		this.description = description;

		bans.add(this);
	}

	public boolean checkBan(Player player) {
		if(!reason.isEnabled())
			return false;

		player.kickPlayer(getKickMessage());
		return true;
	}
	
	public boolean checkBan(PlayerLoginEvent event) {
		if(!reason.isEnabled())
			return false;

		event.disallow(Result.KICK_BANNED, getKickMessage());
		return true;
	}
	
	public String getKickMessage() {
		return "§cYou have been banned from all Varo Servers!\n§cReason: §f" + reason.toString() + "\n§cDescription: §f" + description != null ? description : "None" + "\n§7Unban here: §ahttps://discord.gg/CnDSVVx";
	}

	public String getUuid() {
		return this.uuid;
	}

	public static VaroPlayerBan getBan(String uuid) {
		for(VaroPlayerBan ban : bans)
			if(ban.getUuid().equals(uuid))
				return ban;

		return null;
	}

	public static void loadBans() {
		bans.clear();

		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL("https://varoplugin.de/varo/blocked");
					Scanner scanner = new Scanner(url.openStream());
					while(scanner.hasNext()) {
						String[] block = scanner.next().split(";");

						new VaroPlayerBan(block[0], block.length > 1 ? VaroPlayerBanReason.getByName(block[1]) : VaroPlayerBanReason.OTHER, block.length > 2 ? block[2] : null);
					}

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
								if(getBan(vp.getUuid()) != null)
									getBan(vp.getUuid()).checkBan(vp.getPlayer());
							}
						}
					}, 1);
				} catch(Exception e) {}
			}
		}, 1);
	}
}