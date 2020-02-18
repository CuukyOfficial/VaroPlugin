package de.cuuky.varo.configuration.placeholder;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
import de.cuuky.varo.utils.PermissionUtils;
import de.cuuky.varo.version.VersionUtils;

public class MessagePlaceholderLoader {

	public MessagePlaceholderLoader() {
		loadPlaceHolder();
	}

	private void loadPlaceHolder() {
		// General
		new GeneralMessagePlaceholder("projectname", 10) {

			@Override
			protected String getValue() {
				return Main.getProjectName();
			}
		};

		new GeneralMessagePlaceholder("colorcode", 10) {

			@Override
			protected String getValue() {
				return Main.getColorCode();
			}
		};

		new GeneralMessagePlaceholder("remaining", 1) {

			@Override
			protected String getValue() {
				return String.valueOf(VaroPlayer.getAlivePlayer().size());
			}
		};

		new GeneralMessagePlaceholder("players", 1) {

			@Override
			protected String getValue() {
				return String.valueOf(VaroPlayer.getVaroPlayer().size());
			}
		};

		new GeneralMessagePlaceholder("online", 1) {

			@Override
			protected String getValue() {
				return String.valueOf(VersionUtils.getOnlinePlayer().size());
			}
		};

		new GeneralMessagePlaceholder("currHour", 1) {

			@Override
			protected String getValue() {
				return new SimpleDateFormat("HH").format(new Date());
			}
		};

		new GeneralMessagePlaceholder("currMin", 1) {

			@Override
			protected String getValue() {
				return new SimpleDateFormat("mm").format(new Date());
			}
		};

		new GeneralMessagePlaceholder("currSec", 1) {

			@Override
			protected String getValue() {
				return new SimpleDateFormat("ss").format(new Date());
			}
		};

		new GeneralMessagePlaceholder("bordersize", 1) {

			@Override
			protected String getValue() {
				return !Main.isBootedUp() ? "0" : String.valueOf((int) Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderSize(null));
			}
		};

		new GeneralMessagePlaceholder("discordLink", 20) {

			@Override
			protected String getValue() {
				return ConfigEntry.DISCORDBOT_INVITELINK.getValueAsString();
			}
		};

		new GeneralMessagePlaceholder("protectionTime", 5) {

			@Override
			protected String getValue() {
				return String.valueOf(ConfigEntry.JOIN_PROTECTIONTIME.getValueAsInt());
			}
		};

		new GeneralMessagePlaceholder("&", -1, true) {

			@Override
			protected String getValue() {
				return "§";
			}
		};

		new GeneralMessagePlaceholder("heart", -1) {

			@Override
			protected String getValue() {
				return "♥";
			}
		};

		new GeneralMessagePlaceholder("nextLine", -1) {

			@Override
			protected String getValue() {
				return "\n";
			}
		};

		new GeneralMessagePlaceholder("null", -1) {

			@Override
			protected String getValue() {
				return "";
			}
		};

		// Player
		new PlayerMessagePlaceholder("distanceToBorder", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf((int) Main.getVaroGame().getVaroWorld().getVaroBorder().getBorderDistanceTo(player.getPlayer()));
			}
		};

		new PlayerMessagePlaceholder("min", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getStats().getCountdownMin(player.getStats().getCountdown());
			}
		};

		new PlayerMessagePlaceholder("sec", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getStats().getCountdownSec(player.getStats().getCountdown());
			}
		};
		
		new PlayerMessagePlaceholder("kills", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getKills());
			}
		};
		
		new PlayerMessagePlaceholder("strikes", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getStrikes().size());
			}
		};
		
		new PlayerMessagePlaceholder("teamKills", 2) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getTeam() != null ? player.getTeam().getKills() : 0);
			}
		};
		
		new PlayerMessagePlaceholder("teamLifes", 2) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf((player.getTeam() != null ? player.getTeam().getLifes() : 0));
			}
		};
		
		new PlayerMessagePlaceholder("player", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getName();
			}
		};
		
		new PlayerMessagePlaceholder("prefix", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getPrefix();
			}
		};
		
		new PlayerMessagePlaceholder("team", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getTeam() != null ? player.getTeam().getDisplay() : "-";
			}
		};
		
		new PlayerMessagePlaceholder("rank", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return player.getRank() != null ? player.getRank().getDisplay() : "-";
			}
		};
		
		new PlayerMessagePlaceholder("episodesPlayedPlus1", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getSessionsPlayed() + 1);
			}
		};
		
		new PlayerMessagePlaceholder("sessions", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getSessions());
			}
		};
		
		new PlayerMessagePlaceholder("seconds", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getStats().getCountdown());
			}
		};
		
		new PlayerMessagePlaceholder("remainingDisconnects", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(VaroPlayerDisconnect.getDisconnect(player.getPlayer()) != null ? ConfigEntry.DISCONNECT_PER_SESSION.getValueAsInt() - VaroPlayerDisconnect.getDisconnect(player.getPlayer()).getDisconnects() : ConfigEntry.DISCONNECT_PER_SESSION.getValueAsInt());
			}
		};
		
		new PlayerMessagePlaceholder("ping", 1) {

			@Override
			protected String getValue(VaroPlayer player) {
				return String.valueOf(player.getNetworkManager().getPing());
			}
		};
		
		new PlayerMessagePlaceholder("pexPrefix", 10) {

			@Override
			protected String getValue(VaroPlayer player) {
				return PermissionUtils.getPermissionsExPrefix(player);
			}
		};
		
		new PlayerMessagePlaceholder("lpPrefix", 10) {

			@Override
			protected String getValue(VaroPlayer player) {
				return PermissionUtils.getLuckPermsPrefix(player);
			}
		};
	}
}