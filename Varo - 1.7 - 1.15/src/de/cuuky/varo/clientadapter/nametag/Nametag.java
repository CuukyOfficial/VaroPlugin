package de.cuuky.varo.clientadapter.nametag;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.team.VaroTeam;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class Nametag {

	private static ArrayList<Nametag> nametags;

	private static Class<?> teamClass;
	private static Object configVisibility;
	private static Method setVisibilityMethod, getVisibilityMethod;

	static {
		nametags = new ArrayList<>();

		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			try {
				Class<?> visibilityClass = Class.forName("org.bukkit.scoreboard.NameTagVisibility");

				configVisibility = !ConfigSetting.NAMETAGS.getValueAsBoolean() ? visibilityClass.getDeclaredField("NEVER").get(null) : visibilityClass.getDeclaredField("ALWAYS").get(null);
				teamClass = Class.forName("org.bukkit.scoreboard.Team");
				setVisibilityMethod = teamClass.getDeclaredMethod("setNameTagVisibility", configVisibility.getClass());
				getVisibilityMethod = teamClass.getDeclaredMethod("getNameTagVisibility");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean init, hearts;
	private Player player;
	private String prefix, suffix, name;
	private Rank rank;
	private VaroTeam varoTeam;
	private UUID uniqueID;

	public Nametag(UUID uniqueID, Player p) {
		this.hearts = ConfigMessages.NAMETAG_SUFFIX.getValue().contains("%hearts%");
		this.player = p;
		this.uniqueID = uniqueID;

		for(Team t : p.getScoreboard().getTeams())
			if(!t.getName().startsWith("team-"))
				t.unregister();

		startDelayedRefresh();

		nametags.add(this);
	}

	private void startDelayedRefresh() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				init = true;
				refresh();
			}
		}, 1);
	}

	private void refreshPrefix() {
		VaroPlayer varoPlayer = VaroPlayer.getPlayer(this.player);

		this.rank = varoPlayer.getRank();
		this.varoTeam = varoPlayer.getTeam();
		this.name = checkName();

		this.prefix = (varoTeam == null ? ConfigMessages.NAMETAG_NORMAL.getValue(varoPlayer) : ConfigMessages.NAMETAG_TEAM_PREFIX.getValue(varoPlayer));

		if(this.prefix.length() > 16)
			this.prefix = ConfigMessages.NAMETAG_NORMAL.getValue();

		this.suffix = String.valueOf(ConfigMessages.NAMETAG_SUFFIX.getValue(varoPlayer).replace("%hearts%", String.valueOf(VersionUtils.getHearts(this.player))).replace("%heart%", "♥"));

		if(this.suffix.length() > 16)
			this.suffix = "";
	}

	private String checkName() {
		String name = this.getPlayer().getName();

		int teamsize = VaroTeam.getHighestNumber() + 1;
		int ranks = Rank.getHighestLocation() + 1;

		if(varoTeam != null)
			name = varoTeam.getId() + name;
		else
			name = teamsize + name;

		if(rank != null)
			name = rank.getTablistLocation() + name;
		else
			name = ranks + name;

		if(name.length() > 16)
			name = name.substring(0, 16);
		return name;
	}

	private Object getVisibility(Team team) {
		try {
			return getVisibilityMethod.invoke(team);
		} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void setVisibility(Team team) {
		if(configVisibility == null || getVisibility(team).equals(configVisibility))
			return;

		try {
			setVisibilityMethod.invoke(team, configVisibility);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void updateFor(Scoreboard board, Nametag nametag) {
		Team team = board.getTeam(nametag.getName());

		if(team == null) {
			team = board.registerNewTeam(nametag.getName());
			team.addPlayer(nametag.getPlayer());
		}

		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			setVisibility(team);

		if(nametag.getPrefix() != null) {
			if(team.getPrefix() == null)
				team.setPrefix(nametag.getPrefix());
			else if(!team.getPrefix().equals(nametag.getPrefix()))
				team.setPrefix(nametag.getPrefix());
		}

		if(nametag.getSuffix() != null) {
			if(team.getSuffix() == null)
				team.setSuffix(nametag.getSuffix());
			else if(!team.getSuffix().equals(nametag.getSuffix()))
				team.setSuffix(nametag.getSuffix());
		}
	}

	public void giveAll() {
		if(!init)
			return;

		Player toSet = this.player;
		Scoreboard board = toSet.getScoreboard();
		for(Nametag nametag : nametags) {
			if(!nametag.isOnline() || nametag.getName() == null)
				continue;

			updateFor(board, nametag);
		}
	}

	public void setToAll() {
		if(!init)
			return;

		for(Player toSet : Bukkit.getOnlinePlayers())
			updateFor(toSet.getScoreboard(), this);
	}

	public void heartsChanged() {
		if(!init || !hearts)
			return;

		this.suffix = String.valueOf(ConfigMessages.NAMETAG_SUFFIX.getValue(VaroPlayer.getPlayer(player)).replace("%hearts%", String.valueOf((int) VersionUtils.getHearts(this.player))).replace("%heart%", "♥"));
		setToAll();
	}

	public void refresh() {
		refreshPrefix();
		setToAll();
		giveAll();
	}

	public void remove() {
		nametags.remove(this);
	}

	public String getName() {
		return this.name;
	}

	public Player getPlayer() {
		return player;
	}

	public String getPrefix() {
		return prefix;
	}

	public Rank getRank() {
		return rank;
	}

	public String getSuffix() {
		return suffix;
	}

	public UUID getUniqueId() {
		return this.uniqueID;
	}

	public boolean isOnline() {
		return player != null;
	}

	public static void refreshAll() {
		for(Nametag nametag : nametags) {
			if(!nametag.isOnline())
				continue;

			nametag.refresh();
		}
	}

	public static void refreshGroups(Rank rank) {
		for(Nametag nametag : nametags) {
			if(!nametag.isOnline())
				continue;

			if(!nametag.getRank().equals(rank))
				continue;

			nametag.refresh();
		}
	}

	public static void refreshUser(String user) {
		for(Nametag nametag : nametags) {
			if(!nametag.isOnline())
				continue;

			if(!nametag.getPlayer().getName().equalsIgnoreCase(user))
				continue;

			nametag.refresh();
		}
	}

	public static void resendAll() {
		for(Nametag nametag : nametags) {
			if(!nametag.isOnline())
				continue;

			nametag.setToAll();
			nametag.giveAll();
		}
	}
}