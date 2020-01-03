package de.cuuky.varo.scoreboard.nametag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.config.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class Nametag {

	private static List<Nametag> nametags = new ArrayList<>();
	private static Class<?> teamClass;
	private static Object visibility;

	static {
		nametags = new ArrayList<>();

		if(VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7)) {
			try {
				Class<?> visibilityClass = Class.forName("org.bukkit.scoreboard.NameTagVisibility");

				visibility = !ConfigEntry.NAMETAGS.getValueAsBoolean() ? visibilityClass.getDeclaredField("NEVER").get(null) : visibilityClass.getDeclaredField("ALWAYS").get(null);
				teamClass = Class.forName("org.bukkit.scoreboard.Team");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean init, hearts;
	private Player p;
	private String prefix, suffix, name;
	private Rank rank;
	private de.cuuky.varo.entity.team.Team team;
	private UUID uniqueID;

	public Nametag(UUID uniqueID, Player p) {
		this.hearts = ConfigMessages.NAMETAG_SUFFIX.getValue().contains("%hearts%");

		this.p = p;
		p.getScoreboard().getTeams().forEach(team -> team.unregister());
		this.uniqueID = uniqueID;

		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				init = true;
				refresh();
			}
		}, 1);

		nametags.add(this);
	}

	public String getName() {
		return this.name;
	}

	public Player getPlayer() {
		return p;
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

	public void giveAll() {
		if(!init)
			return;

		Player toSet = this.p;
		Scoreboard board = toSet.getScoreboard();
		for(Nametag nametag : nametags) {
			if(!nametag.isOnline())
				continue;

			Team team = board.getTeam(nametag.getName());

			try {
				board.getTeam(nametag.getName()).unregister();
			} catch(NullPointerException e) {}

			team = board.registerNewTeam(nametag.getName());
			team.addPlayer(nametag.getPlayer());

			setVisibility(team);
			if(nametag.getPrefix() != null)
				team.setPrefix(nametag.getPrefix());

			if(nametag.getSuffix() != null)
				team.setSuffix(nametag.getSuffix());
		}

		toSet.setScoreboard(board);
	}

	public void heartsChanged() {
		if(!init || !hearts)
			return;

		this.suffix = String.valueOf(ConfigMessages.NAMETAG_SUFFIX.getValue(VaroPlayer.getPlayer(p)).replace("%hearts%", String.valueOf((int) p.getHealth())).replace("%heart%", "♥"));
		setToAll();
	}

	public boolean isOnline() {
		return p != null;
	}

	public void nameTagVisibilityReset() {
		if(!init)
			return;

		for(Player toSet : Bukkit.getOnlinePlayers()) {
			Scoreboard board = toSet.getScoreboard();
			Team team = getTeam(board);

			setVisibility(team);
			toSet.setScoreboard(board);
		}
	}

	public void prefixReset() {
		if(!init)
			return;

		for(Player toSet : Bukkit.getOnlinePlayers()) {
			Scoreboard board = toSet.getScoreboard();
			Team team = getTeam(board);

			team.setPrefix(this.prefix);
			toSet.setScoreboard(board);
		}
	}

	public void refresh() {
		refreshPrefix();
		setToAll();
		giveAll();
	}

	public void refreshPrefix() {
		VaroPlayer player = VaroPlayer.getPlayer(p);

		this.rank = player.getRank();
		this.team = player.getTeam();
		this.name = checkName();

		this.prefix = (team == null ? ConfigMessages.NAMETAG_NORMAL.getValue(player) : ConfigMessages.NAMETAG_TEAM_PREFIX.getValue(player));

		if(prefix.length() > 16)
			prefix = ConfigMessages.NAMETAG_NORMAL.getValue();

		this.suffix = String.valueOf(ConfigMessages.NAMETAG_SUFFIX.getValue(player).replace("%hearts%", String.valueOf((int) player.getPlayer().getHealth())).replace("%heart%", "♥"));
	}

	public void remove() {
		nametags.remove(this);
	}

	public void setToAll() {
		if(!init)
			return;

		for(Player toSet : Bukkit.getOnlinePlayers()) {
			Scoreboard board = toSet.getScoreboard();
			Team team = board.getTeam(this.name);

			try {
				board.getTeam(this.name).unregister();
			} catch(NullPointerException e) {}

			team = board.registerNewTeam(this.name);
			team.addPlayer(this.p.getPlayer());

			setVisibility(team);
			if(this.prefix != null)
				team.setPrefix(this.prefix);

			if(this.suffix != null)
				team.setSuffix(this.suffix);

			toSet.setScoreboard(board);
		}
	}

	public void suffixReset() {
		if(!init)
			return;

		for(Player toSet : Bukkit.getOnlinePlayers()) {
			Scoreboard board = toSet.getScoreboard();
			Team team = getTeam(board);

			team.setSuffix(this.suffix);
			toSet.setScoreboard(board);
		}
	}

	private String checkName() {
		String name = this.getPlayer().getName();

		int teamsize = de.cuuky.varo.entity.team.Team.getHighestNumber() + 1;
		int ranks = Rank.getHighestLocation() + 1;

		if(team != null)
			name = team.getId() + name;
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

	private Team getTeam(Scoreboard board) {
		Team team = board.getTeam(this.name);

		if(team == null) {
			team = board.registerNewTeam(this.name);
			team.addPlayer(this.p);
		}

		return team;
	}

	private void setVisibility(Team team) {
		if(visibility == null)
			return;

		try {
			teamClass.getDeclaredMethod("setNameTagVisibility", visibility.getClass()).invoke(team, visibility);
		} catch(Exception e) {
			e.printStackTrace();
		}
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