package de.cuuky.varo.api.objects.player;

import java.util.ArrayList;
import java.util.List;

import de.cuuky.varo.api.objects.player.stats.VaroAPIStats;
import de.cuuky.varo.api.objects.team.VaroAPITeam;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroAPIPlayer {

	private VaroPlayer vp;

	public VaroAPIPlayer(VaroPlayer vp) {
		this.vp = vp;
	}

	public int getId() {
		return vp.getId();
	}

	public String getName() {
		return vp.getName();
	}

	public VaroAPIStats getStats() {
		return new VaroAPIStats(vp.getStats());
	}

	public VaroAPITeam getTeam() {
		return vp.getTeam() == null ? null : new VaroAPITeam(vp.getTeam());
	}

	public String getUUID() {
		return vp.getUUID();
	}

	public static List<VaroAPIPlayer> getAlivePlayers() {
		ArrayList<VaroAPIPlayer> alive = new ArrayList<VaroAPIPlayer>();
		for (VaroPlayer vp : VaroPlayer.getAlivePlayer())
			alive.add(new VaroAPIPlayer(vp));

		return alive;
	}

	public static List<VaroAPIPlayer> getOnlinePlayers() {
		ArrayList<VaroAPIPlayer> alive = new ArrayList<VaroAPIPlayer>();
		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
			alive.add(new VaroAPIPlayer(vp));

		return alive;
	}

	public static List<VaroAPIPlayer> getVaroPlayers() {
		ArrayList<VaroAPIPlayer> alive = new ArrayList<VaroAPIPlayer>();
		for (VaroPlayer vp : VaroPlayer.getVaroPlayers())
			alive.add(new VaroAPIPlayer(vp));

		return alive;
	}
}