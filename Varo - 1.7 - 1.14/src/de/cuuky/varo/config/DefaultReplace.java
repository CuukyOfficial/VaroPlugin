package de.cuuky.varo.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.disconnect.Disconnect;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.utils.PermissionUtils;
import de.cuuky.varo.version.VersionUtils;

public class DefaultReplace {

	private String replace;

	public DefaultReplace(String replace) {
		this.replace = replace;
	}

	public String getReplaced() {
		String replaced = replace;
		replaced = replaced.contains("%projectname%") ? replaced.replaceAll("%projectname%", Main.getProjectName()) : replaced;
		replaced = replaced.contains("%remaining%") ? replaced.replaceAll("%remaining%", String.valueOf(VaroPlayer.getAlivePlayer().size())) : replaced;
		replaced = replaced.contains("%players%") ? replaced.replaceAll("%players%", String.valueOf(VaroPlayer.getVaroPlayer().size())) : replaced;
		replaced = replaced.contains("%online%") ? replaced.replaceAll("%online%", String.valueOf(VersionUtils.getOnlinePlayer().size())) : replaced;
		replaced = replaced.contains("%currHour%") ? replaced.replaceAll("%currHour%", new SimpleDateFormat("HH").format(new Date())) : replaced;
		replaced = replaced.contains("%currMin%") ? replaced.replaceAll("%currMin%", new SimpleDateFormat("mm").format(new Date())) : replaced;
		replaced = replaced.contains("%currSec%") ? replaced.replaceAll("%currSec%", new SimpleDateFormat("ss").format(new Date())) : replaced;
		replaced = replaced.contains("%bordersize%") ? replaced.replaceAll("%bordersize%", !Main.isBootedUp() ? "0" : String.valueOf((int) Main.getDataManager().getWorldHandler().getBorder().getSize())) : replaced;
		replaced = replaced.contains("%colorcode%") ? replaced.replaceAll("%colorcode%", Main.getColorCode()) : replaced;
		replaced = replaced.contains("%discordLink%") ? replaced.replaceAll("%discordLink%", ConfigEntry.DISCORDBOT_INVITELINK.getValueAsString()) : replaced;
		replaced = replaced.contains("%protectionTime%") ? replaced.replaceAll("%protectionTime%", ConfigEntry.JOIN_PROTECTIONTIME.getValueAsString()) : replaced;

		return replaced.replaceAll("&", "§").replaceAll("%heart%", "♥").replaceAll("%nextLine%", "\n").replace("%null%", "");
	}

	public String getReplaced(VaroPlayer vp) {
		String replaced = getReplaced();
		replaced = replaced.contains("%distanceToBorder%") ? replaced.replace("%distanceToBorder%", String.valueOf((int) Main.getDataManager().getWorldHandler().getBorder().getDistanceTo(vp.getPlayer()))) : replaced;
		replaced = replaced.contains("%min%") ? replaced.replaceAll("%min%", vp.getStats().getCountdownMin(vp.getStats().getCountdown())) : replaced;
		replaced = replaced.contains("%sec%") ? replaced.replaceAll("%sec%", vp.getStats().getCountdownSec(vp.getStats().getCountdown())) : replaced;
		replaced = replaced.contains("%kills%") ? replaced.replaceAll("%kills%", String.valueOf(vp.getStats().getKills())) : replaced;
		replaced = replaced.contains("%strikes%") ? replaced.replaceAll("%strikes%", String.valueOf(vp.getStats().getStrikes().size())) : replaced;
		replaced = replaced.contains("%teamKills%") ? replaced.replaceAll("%teamKills%", String.valueOf(vp.getTeam() != null ? vp.getTeam().getKills() : 0)) : replaced;
		replaced = replaced.contains("%teamLifes%") ? replaced.replaceAll("%teamLifes%", String.valueOf((vp.getTeam() != null ? vp.getTeam().getLifes() : 0))) : replaced;
		replaced = replaced.contains("%player%") ? replaced.replaceAll("%player%", vp.getName()) : replaced;
		replaced = replaced.contains("%prefix%") ? replaced.replaceAll("%prefix%", vp.getPrefix()) : replaced;
		replaced = replaced.contains("%team%") ? replaced.replaceAll("%team%", vp.getTeam() != null ? vp.getTeam().getDisplay() : "-") : replaced;
		replaced = replaced.contains("%rank%") ? replaced.replaceAll("%rank%", vp.getRank() != null ? vp.getRank().getDisplay() : "-") : replaced;
		replaced = replaced.contains("%episodesPlayedPlus1%") ? replaced.replaceAll("%episodesPlayedPlus1%", String.valueOf(vp.getStats().getSessionsPlayed() + 1)) : replaced;
		replaced = replaced.contains("%sessions%") ? replaced.replaceAll("%sessions%", String.valueOf(vp.getStats().getSessions())) : replaced;
		replaced = replaced.contains("%seconds%") ? replaced.replaceAll("%seconds%", String.valueOf(vp.getStats().getCountdown())) : replaced;
		replaced = replaced.contains("%remainingDisconnects%") ? replaced.replaceAll("%remainingDisconnects%", String.valueOf(Disconnect.getDisconnect(vp.getPlayer()) != null ? ConfigEntry.DISCONNECT_PER_SESSION.getValueAsInt() - Disconnect.getDisconnect(vp.getPlayer()).getDisconnects() : ConfigEntry.DISCONNECT_PER_SESSION.getValueAsInt())) : replaced;
		replaced = replaced.contains("%ping%") ? replaced.replaceAll("%ping%", String.valueOf(vp.getNetworkManager().getPing())) : replaced;
		replaced = replaced.contains("%pexPrefix%") ? replaced.replaceAll("%pexPrefix%", PermissionUtils.getPermissionsExPrefix(vp)) : replaced;
		replaced = replaced.contains("%lpPrefix%") ? replaced.replaceAll("%lpPrefix%", PermissionUtils.getLuckPermsPrefix(vp)) : replaced;

		return replaced;
	}
}
