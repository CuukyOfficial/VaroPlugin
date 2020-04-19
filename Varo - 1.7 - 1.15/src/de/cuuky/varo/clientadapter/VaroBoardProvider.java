package de.cuuky.varo.clientadapter;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import de.cuuky.cfw.clientadapter.board.BoardUpdateHandler;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.clientadapter.list.lists.ScoreboardBoardList;
import de.cuuky.varo.clientadapter.list.lists.TablistBoardList;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.languages.defaults.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Rank;
import de.cuuky.varo.entity.team.VaroTeam;

public class VaroBoardProvider implements BoardUpdateHandler {

	private ScoreboardBoardList scoreboard;
	private TablistBoardList tablist;

	public VaroBoardProvider() {
		this.scoreboard = new ScoreboardBoardList();
		this.tablist = new TablistBoardList();

		update();
	}

	private ArrayList<String> replaceList(ArrayList<String> list, VaroPlayer vp) {
		ArrayList<String> newList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String line = list.get(i);
			if (line.contains("%min%") || line.contains("%sec%"))
				if (ConfigSetting.PLAY_TIME.getValueAsInt() < 1)
					line = "§cUnlimited";

			line = Main.getLanguageManager().replaceMessage(line, vp);

			newList.add(line);
		}

		return newList;
	}

	public void update() {
		scoreboard.update();
		tablist.update();
	}

	@Override
	public ArrayList<String> getTablistHeader(Player player) {
		return replaceList(tablist.getHeaderLines(), VaroPlayer.getPlayer(player));
	}

	@Override
	public ArrayList<String> getTablistFooter(Player player) {
		return replaceList(tablist.getFooterLines(), VaroPlayer.getPlayer(player));
	}

	@Override
	public String getTablistName(Player player) {
		String listname = "";
		VaroPlayer vp = VaroPlayer.getPlayer(player);
		if (vp.getTeam() != null) {
			if (vp.getRank() == null) {
				listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM.getValue(null, vp);
			} else {
				listname = ConfigMessages.TABLIST_PLAYER_WITH_TEAM_RANK.getValue(null, vp);
			}
		} else {
			if (vp.getRank() == null) {
				listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM.getValue(null, vp);
			} else {
				listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM_RANK.getValue(null, vp);
			}
		}

		int maxlength = BukkitVersion.ONE_8.isHigherThan(VersionUtils.getVersion()) ? 16 : -1;
		if (maxlength > 0)
			if (listname.length() > maxlength)
				listname = ConfigMessages.TABLIST_PLAYER_WITHOUT_TEAM_RANK.getValue(null, vp);

		return listname;
	}

	@Override
	public String getScoreboardTitle(Player player) {
		return Main.getLanguageManager().replaceMessage(scoreboard.getHeader(), VaroPlayer.getPlayer(player));
	}

	@Override
	public ArrayList<String> getScoreboardEntries(Player player) {
		return replaceList(scoreboard.getScoreboardLines(), VaroPlayer.getPlayer(player));
	}

	@Override
	public boolean showHeartsBelowName(Player player) {
		return false;
	}

	@Override
	public String getNametagName(Player player) {
		VaroPlayer vplayer = VaroPlayer.getPlayer(player);
		String name = vplayer.getName();

		int teamsize = VaroTeam.getHighestNumber() + 1;
		int ranks = Rank.getHighestLocation() + 1;

		if (vplayer.getTeam() != null)
			name = vplayer.getTeam().getId() + name;
		else
			name = teamsize + name;

		if (vplayer.getRank() != null)
			name = vplayer.getRank().getTablistLocation() + name;
		else
			name = ranks + name;

		return name;
	}

	@Override
	public String getNametagPrefix(Player player) {
		VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
		String prefix = (varoPlayer.getTeam() == null ? ConfigMessages.NAMETAG_NORMAL.getValue(null, varoPlayer) : ConfigMessages.NAMETAG_TEAM_PREFIX.getValue(null, varoPlayer));

		if (prefix.length() > 16)
			prefix = ConfigMessages.NAMETAG_NORMAL.getValue(null, varoPlayer);

		return prefix;
	}

	@Override
	public String getNametagSuffix(Player player) {
		return String.valueOf(ConfigMessages.NAMETAG_SUFFIX.getValue(null, VaroPlayer.getPlayer(player)));
	}

	@Override
	public boolean isNametagVisible(Player player) {
		return ConfigSetting.NAMETAGS_ENABLED.getValueAsBoolean();
	}
}