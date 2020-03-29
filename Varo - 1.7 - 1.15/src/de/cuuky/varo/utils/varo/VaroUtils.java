package de.cuuky.varo.utils.varo;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public final class VaroUtils {

	private static ArrayList<String> blocked;

	public static void loadBlock() {
		blocked = new ArrayList<>();

		Bukkit.getScheduler().scheduleAsyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL("https://varoplugin.de/varo/blocked");
					Scanner scanner = new Scanner(url.openStream());
					while(scanner.hasNext()) {
						String block = scanner.next();
						blocked.add(block);
					}

					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							for(VaroPlayer vp : VaroPlayer.getVaroPlayer()) {
								if(blocked.contains(vp.getUuid()))
									if(vp.isOnline())
										vp.getPlayer().kickPlayer("§cYou have been banned from all Varo Servers!\n§7Unban here: §ahttps://discord.gg/CnDSVVx");
							}
						}
					}, 1);
				} catch(Exception e) {}
			}
		}, 1);
	}

	public static boolean check(VaroPlayer vp, PlayerLoginEvent event) {
		if(blocked.contains(vp.getUuid())) {
			event.disallow(Result.KICK_OTHER, "§cYou have been banned from all Varo Servers!\n§7Unban here: §ahttps://discord.gg/CnDSVVx");
			return true;
		}
		
		return false;
	}

	private static int worldToTimeID = 0;

	public static void setWorldToTime() {
		if(!ConfigSetting.ALWAYS_TIME.isIntActivated())
			return;

		if(worldToTimeID != 0)
			Bukkit.getScheduler().cancelTask(worldToTimeID);

		worldToTimeID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			int time = ConfigSetting.ALWAYS_TIME.getValueAsInt();

			@Override
			public void run() {
				if(Main.getVaroGame().hasStarted() && !ConfigSetting.ALWAYS_TIME_USE_AFTER_START.getValueAsBoolean()) {
					Bukkit.getScheduler().cancelTask(worldToTimeID);
					return;
				}

				for(World world : Bukkit.getWorlds()) {
					world.setTime(time);
					world.setThundering(false);
					world.setStorm(false);
				}
			}
		}, 0, 40);
	}

	public static void doRandomTeam(int teamSize) {
		if(teamSize >= 2) {
			ArrayList<VaroPlayer> finished = new ArrayList<>();
			for(VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
				if(finished.contains(vp) || vp.getStats().isSpectator() || vp.getTeam() != null)
					continue;

				ArrayList<VaroPlayer> teamMember = new ArrayList<>();
				teamMember.add(vp);
				finished.add(vp);

				int missingMember = teamSize - 1;
				for(VaroPlayer othervp : VaroPlayer.getOnlinePlayer()) {
					if(missingMember == 0)
						break;

					if(finished.contains(othervp) || othervp.getStats().isSpectator() || othervp.getTeam() != null)
						continue;

					teamMember.add(othervp);
					finished.add(othervp);
					missingMember--;
				}

				if(teamMember.size() != teamSize)
					vp.getPlayer().sendMessage(Main.getPrefix() + "§7Fuer dich wurden nicht genug" + Main.getColorCode() + " Teampartner §7gefunden!");

				String teamName = "";
				for(VaroPlayer teamPl : teamMember)
					teamName = teamName + teamPl.getName().substring(0, teamPl.getName().length() / teamSize);

				VaroTeam team = new VaroTeam(teamName);
				for(VaroPlayer teamPl : teamMember)
					team.addMember(teamPl);
			}
		} else if(teamSize == 1) {
			for(VaroPlayer pl : VaroPlayer.getOnlinePlayer()) {
				if(pl.getTeam() != null || pl.getStats().isSpectator())
					continue;

				new VaroTeam(pl.getName().length() == 16 ? pl.getName().substring(0, 15) : pl.getName()).addMember(pl);
			}
		}
	}
}