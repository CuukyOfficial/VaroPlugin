package de.cuuky.varo.configuration.placeholder;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.util.DateInfo;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
import de.cuuky.varo.utils.PermissionUtils;
import de.cuuky.varo.version.VersionUtils;

public class MessagePlaceholderLoader {

	public MessagePlaceholderLoader() {
		loadMessagePlaceHolder();
		loadPlayerPlaceholder();
	}

	private void loadMessagePlaceHolder() {
		new GeneralMessagePlaceholder("projectname", 10, "Ersetzt durch den Projektnamen") {

			@Override
			protected String getValue() {
				return Main.getProjectName();
			}
		};

		new GeneralMessagePlaceholder("colorcode", 10, "Ersetzt durch den Farbcode") {

			@Override
			protected String getValue() {
				return Main.getColorCode();
			}
		};

		new GeneralMessagePlaceholder("remaining", 1, "Ersetzt durch die Anzahl lebender Spieler") {

			@Override
			protected String getValue() {
				return String.valueOf(VaroPlayer.getAlivePlayer().size());
			}
		};

		new GeneralMessagePlaceholder("players", 1, "Ersetzt durch die Anzahl aller Spieler") {

			@Override
			protected String getValue() {
				return String.valueOf(VaroPlayer.getVaroPlayer().size());
			}
		};

		new GeneralMessagePlaceholder("online", 1, "Ersetzt durch die Anzahl aller online Spieler") {

			@Override
			protected String getValue() {
				return String.valueOf(VersionUtils.getOnlinePlayer().size());
			}
		};

		new GeneralMessagePlaceholder("currYear", 1, "Ersetzt durch das Jahr der jetzigen Zeit") {

			@Override
			protected String getValue() {
				return getLastDateRefresh(DateInfo.YEAR);
			}
		};

		new GeneralMessagePlaceholder("currMonth", 1, "Ersetzt durch den Monat der jetzigen Zeit") {

			@Override
			protected String getValue() {
				return getLastDateRefresh(DateInfo.MONTH);
			}
		};

		new GeneralMessagePlaceholder("currDay", 1, "Ersetzt durch den Tag der jetzigen Zeit") {

			@Override
			protected String getValue() {
				return getLastDateRefresh(DateInfo.DAY);
			}
		};

		new GeneralMessagePlaceholder("currHour", 1, "Ersetzt durch die Stunde der jetzigen Zeit") {

			@Override
			protected String getValue() {
				return getLastDateRefresh(DateInfo.HOUR);
			}
		};

		new GeneralMessagePlaceholder("currMin", 1, "Ersetzt durch die Minute der jetzigen Zeit") {

			@Override
			protected String getValue() {
				return getLastDateRefresh(DateInfo.MINUTES);
			}
		};

		new GeneralMessagePlaceholder("currSec", 1, "Ersetzt durch die Sekunden der jetzigen Zeit") {

			@Override
			protected String getValue() {
				return getLastDateRefresh(DateInfo.SECONDS);
			}
		};

		new GeneralMessagePlaceholder("bordersize", 1, "Ersetzt durch die Bordergroesse") {

			@Override
			protected String getValue() {
				return !Main.isBootedUp() ? "0" : String.valueOf((int) Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderSize(null));
			}
		};

		new GeneralMessagePlaceholder("discordLink", 20, "Ersetzt durch den DiscordLink aus der Config") {

			@Override
			protected String getValue() {
				return ConfigSetting.DISCORDBOT_INVITELINK.getValueAsString();
			}
		};

		new GeneralMessagePlaceholder("protectionTime", 5, "Ersetzt durch die Schutzzeit aus der Config") {

			@Override
			protected String getValue() {
				return String.valueOf(ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt());
			}
		};

		new GeneralMessagePlaceholder("&", -1, true, "Ersetzt durch §") {

			@Override
			protected String getValue() {
				return "§";
			}
		};

		new GeneralMessagePlaceholder("heart", -1, "Ersetzt durch ♥") {

			@Override
			protected String getValue() {
				return "♥";
			}
		};

		new GeneralMessagePlaceholder("nextLine", -1, "Fuegt neue Zeile ein") {

			@Override
			protected String getValue() {
				return "\n";
			}
		};

		new GeneralMessagePlaceholder("null", -1, "Ersetzt durch nichts") {

			@Override
			protected String getValue() {
				return "";
			}
		};
	}

	private void loadPlayerPlaceholder() {
		new PlayerMessagePlaceholder("distanceToBorder", 1, "Ersetzt durch die Distanz zur Border des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf((int) Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderDistanceTo(player.getPlayer()));
			}
		};

		new PlayerMessagePlaceholder("min", 1, "Ersetzt durch die Minuten der Spielzeit des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getStats().getCountdownMin(player.getStats().getCountdown());
			}
		};

		new PlayerMessagePlaceholder("sec", 1, "Ersetzt durch die Sekunden der Spielzeit des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getStats().getCountdownSec(player.getStats().getCountdown());
			}
		};

		new PlayerMessagePlaceholder("kills", 1, "Ersetzt durch Kills des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getKills());
			}
		};

		new PlayerMessagePlaceholder("strikes", 1, "Ersetzt durch die Strikes des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getStrikes().size());
			}
		};

		new PlayerMessagePlaceholder("teamKills", 2, "Ersetzt durch die Teamkills des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getTeam() != null ? player.getTeam().getKills() : 0);
			}
		};

		new PlayerMessagePlaceholder("teamLifes", 2, "Ersetzt durch die Teamleben des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf((player.getTeam() != null ? player.getTeam().getLifes() : 0));
			}
		};

		new PlayerMessagePlaceholder("player", 1, "Ersetzt durch den Namen des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getName();
			}
		};

		new PlayerMessagePlaceholder("prefix", 1, "Ersetzt durch den Prefix des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getPrefix();
			}
		};

		new PlayerMessagePlaceholder("team", 1, "Ersetzt durch den Teamname des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getTeam() != null ? player.getTeam().getDisplay() : "-";
			}
		};

		new PlayerMessagePlaceholder("rank", 1, "Ersetzt durch den Rangnamen des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getRank() != null ? player.getRank().getDisplay() : "-";
			}
		};

		new PlayerMessagePlaceholder("episodesPlayedPlus1", 1, "Ersetzt durch die gespielten Episoden+1 des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getSessionsPlayed() + 1);
			}
		};

		new PlayerMessagePlaceholder("sessions", 1, "Ersetzt durch die Sessions des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getSessions());
			}
		};

		new PlayerMessagePlaceholder("seconds", 1, "Ersetzt durch den Countdown des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getCountdown());
			}
		};

		new PlayerMessagePlaceholder("countdown", 1, "Ersetzt durch den Countdown des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getCountdown());
			}
		};

		new PlayerMessagePlaceholder("remainingDisconnects", 1, "Ersetzt durch die Disconnects der Session des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(VaroPlayerDisconnect.getDisconnect(player.getPlayer()) != null ? ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt() - VaroPlayerDisconnect.getDisconnect(player.getPlayer()).getDisconnects() : ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt());
			}
		};

		new PlayerMessagePlaceholder("ping", 1, "Ersetzt durch den Countdown des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getNetworkManager().getPing());
			}
		};

		new PlayerMessagePlaceholder("pexPrefix", 10, "Ersetzt durch den Pex-Prefix des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return PermissionUtils.getPermissionsExPrefix(player);
			}
		};

		new PlayerMessagePlaceholder("worldSpawnX", 1, "Ersetzt durch die X-Koord des Spawns der Welt") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockX()) : "0";
			}
		};

		new PlayerMessagePlaceholder("worldSpawnY", 1, "Ersetzt durch die Y-Koord des Spawns der Welt") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockY()) : "0";
			}
		};

		new PlayerMessagePlaceholder("worldSpawnZ", 1, "Ersetzt durch die Z-Koord des Spawns der Welt") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockZ()) : "0";
			}
		};
		
		new PlayerMessagePlaceholder("playerLocX", 1, "Ersetzt durch die X-Koordinate des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockX()) : "0";
			}
		};
		
		new PlayerMessagePlaceholder("playerLocY", 1, "Ersetzt durch die Y-Koordinate des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockY()) : "0";
			}
		};

		
		new PlayerMessagePlaceholder("playerLocZ", 1, "Ersetzt durch die Z-Koordinate des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockZ()) : "0";
			}
		};


		new PlayerMessagePlaceholder("lpPrefix", 10, "Ersetzt durch den LuckPerms-Prefix des Spielers") {

			@Override
			protected String getValue(VaroPlayer player) {
				return PermissionUtils.getLuckPermsPrefix(player);
			}
		};
	}
}