package de.cuuky.varo.configuration.placeholder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;

import de.cuuky.cfw.configuration.placeholder.placeholder.util.DateInfo;
import de.cuuky.cfw.utils.PermissionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.placeholder.varo.VaroGeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.varo.VaroPlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;

public class MessagePlaceholderLoader {

    private long lastDateRefreshTime;
    private String[] lastDateRefresh;

    public MessagePlaceholderLoader() {
        loadMessagePlaceHolder();
        loadPlayerPlaceholder();
    }

    private String getLastDateRefresh(int index) {
        if (lastDateRefresh == null || lastDateRefreshTime + 900 <= System.currentTimeMillis()) {
            lastDateRefreshTime = System.currentTimeMillis();
            lastDateRefresh = new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss").format(new Date()).split(",");
        }

        return lastDateRefresh[index];
    }

    private void loadMessagePlaceHolder() {
        new VaroGeneralMessagePlaceholder("pluginName", -1, "Ersetzt durch den Plugin-Namen", Main.getInstance().getDescription()::getName);
        new VaroGeneralMessagePlaceholder("pluginAuthor", -1, "Ersetzt durch den Plugin-Macher", () -> Main.getInstance().getDescription().getAuthors().get(0));
        new VaroGeneralMessagePlaceholder("pluginContributors", -1, "Ersetzt durch den Plugin-Contributor", () -> Main.getContributors());
        new VaroGeneralMessagePlaceholder("pluginVersion", -1, "Ersetzt durch die Plugin-Version", Main.getInstance().getDescription()::getVersion);
        new VaroGeneralMessagePlaceholder("projectname", 10, "Ersetzt durch den Projektnamen", Main::getProjectName);
        new VaroGeneralMessagePlaceholder("colorcode", 10, "Ersetzt durch den Farbcode", Main::getColorCode);
        new VaroGeneralMessagePlaceholder("remaining", 1, "Ersetzt durch die Anzahl lebender Spieler", () -> String.valueOf(VaroPlayer.getAlivePlayer().size()));
        new VaroGeneralMessagePlaceholder("players", 1, "Ersetzt durch die Anzahl aller Spieler", () -> String.valueOf(VaroPlayer.getVaroPlayers().size()));
        new VaroGeneralMessagePlaceholder("online", 1, "Ersetzt durch die Anzahl aller online Spieler", () -> String.valueOf(VersionUtils.getVersionAdapter().getOnlinePlayers().size()));
        new VaroGeneralMessagePlaceholder("currYear", 1, "Ersetzt durch das Jahr der jetzigen Zeit", () -> getLastDateRefresh(DateInfo.YEAR));
        new VaroGeneralMessagePlaceholder("currMonth", 1, "Ersetzt durch den Monat der jetzigen Zeit", () -> getLastDateRefresh(DateInfo.MONTH));
        new VaroGeneralMessagePlaceholder("currDay", 1, "Ersetzt durch den Tag der jetzigen Zeit", () -> getLastDateRefresh(DateInfo.DAY));
        new VaroGeneralMessagePlaceholder("currHour", 1, "Ersetzt durch die Stunde der jetzigen Zeit", () -> getLastDateRefresh(DateInfo.HOUR));
        new VaroGeneralMessagePlaceholder("currMin", 1, "Ersetzt durch die Minute der jetzigen Zeit", () -> getLastDateRefresh(DateInfo.MINUTES));
        new VaroGeneralMessagePlaceholder("currSec", 1, "Ersetzt durch die Sekunden der jetzigen Zeit", () -> getLastDateRefresh(DateInfo.SECONDS));
        new VaroGeneralMessagePlaceholder("projectTimeHour", 1, "Ersetzt durch die Stunden der Spielzeit des Projektes", () -> String.format("%02d", Main.getVaroGame().getProjectTime() / 3600));
        new VaroGeneralMessagePlaceholder("projectTimeMin", 1, "Ersetzt durch die Minuten der Spielzeit des Projektes", () -> String.format("%02d", (Main.getVaroGame().getProjectTime() / 60) % 60));
        new VaroGeneralMessagePlaceholder("projectTimeSec", 1, "Ersetzt durch die Sekunden der Spielzeit des Projektes", () -> String.format("%02d", Main.getVaroGame().getProjectTime() % 60));
        new VaroGeneralMessagePlaceholder("bordersize", 1, "Ersetzt durch die Bordergroesse", () -> !Main.isBootedUp() ? "0" : String.valueOf((int) Main.getVaroGame().getVaroWorldHandler().getBorderSize(null)));
        new VaroGeneralMessagePlaceholder("discordLink", 20, "Ersetzt durch den DiscordLink aus der Config", ConfigSetting.DISCORDBOT_INVITELINK::getValueAsString);
        new VaroGeneralMessagePlaceholder("protectionTime", 5, "Ersetzt durch die Schutzzeit aus der Config", () -> String.valueOf(ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt()));
        new VaroGeneralMessagePlaceholder("&", -1, true, "Ersetzt durch §", () -> "§");
        new VaroGeneralMessagePlaceholder("heart", -1, "Ersetzt durch ♥", () -> "♥");
        new VaroGeneralMessagePlaceholder("nextLine", -1, "Fuegt neue Zeile ein", () -> "\n");
        new VaroGeneralMessagePlaceholder("null", -1, "Ersetzt durch nichts", () -> "");

        for (ConfigSetting setting : ConfigSetting.values())
            if (!setting.isSensitive())
                new VaroGeneralMessagePlaceholder(setting.getPath(), 10, String.join(" ", setting.getDescription()), () -> String.valueOf(setting.getValue()).replace("&", "§"));
    }

    private void loadPlayerPlaceholder() {
        new VaroPlayerMessagePlaceholder("distanceToBorder", 1, "Ersetzt durch die Distanz zur Border des Spielers", (player) -> {
            if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7) || Main.getVaroGame() == null)
                return "0";

            return String.valueOf((int) Main.getVaroGame().getVaroWorldHandler().getVaroWorld(player.getPlayer().getWorld()).getVaroBorder().getBorderDistanceTo(player.getPlayer()));
        });

        new VaroPlayerMessagePlaceholder("seconds", 1, "Ersetzt durch den Countdown des Spielers", (player) -> String.valueOf(player.getStats().getCountdown()));
        new VaroPlayerMessagePlaceholder("countdown", 1, "Ersetzt durch den Countdown des Spielers", (player) -> String.valueOf(player.getStats().getCountdown()));
        new VaroPlayerMessagePlaceholder("hour", 1, "Ersetzt durch die verbleibenden Stunden der aktuellen Session des Spielers", (player) -> ConfigSetting.PLAY_TIME.getValueAsInt() < 1 ? "-" : String.format("%02d", player.getStats().getCountdown() / 3600));
        new VaroPlayerMessagePlaceholder("min", 1, "Ersetzt durch die verbleibenden Minuten der aktuellen Session des Spielers", (player) -> ConfigSetting.PLAY_TIME.getValueAsInt() < 1 ? "-" : String.format("%02d", (player.getStats().getCountdown() / 60) % 60));
        new VaroPlayerMessagePlaceholder("sec", 1, "Ersetzt durch die verbleibenden Sekunden der aktuellen Session des Spielers", (player) -> ConfigSetting.PLAY_TIME.getValueAsInt() < 1 ? "-" : String.format("%02d", player.getStats().getCountdown() % 60));
        new VaroPlayerMessagePlaceholder("sessionTimeHour", 1, "Ersetzt durch die Stunden der Spielzeit des Spielers in dieser Session", (player) -> String.format("%02d", player.getStats().getSessionTime() / 3600));
        new VaroPlayerMessagePlaceholder("sessionTimeMin", 1, "Ersetzt durch die Minuten der Spielzeit des Spielers in dieser Session", (player) -> String.format("%02d", (player.getStats().getSessionTime() / 60) % 60));
        new VaroPlayerMessagePlaceholder("sessionTimeSec", 1, "Ersetzt durch die Sekunden der Spielzeit des Spielers in dieser Session", (player) -> String.format("%02d", player.getStats().getSessionTime() % 60));
        new VaroPlayerMessagePlaceholder("onlineTimeHour", 1, "Ersetzt durch die Stunden der Spielzeit des Spielers", (player) -> String.format("%02d", player.getStats().getOnlineTime() / 3600));
        new VaroPlayerMessagePlaceholder("onlineTimeMin", 1, "Ersetzt durch die Minuten der Spielzeit des Spielers", (player) -> String.format("%02d", (player.getStats().getOnlineTime() / 60) % 60));
        new VaroPlayerMessagePlaceholder("onlineTimeSec", 1, "Ersetzt durch die Sekunden der Spielzeit des Spielers", (player) -> String.format("%02d", player.getStats().getOnlineTime() % 60));
        new VaroPlayerMessagePlaceholder("onlineTimeTotalHour", 1, "Ersetzt durch die Stunden der gesamten Spielzeit des Spielers", (player) -> String.format("%02d", player.getStats().getOnlineTimeTotal() / 3600));
        new VaroPlayerMessagePlaceholder("onlineTimeTotalMin", 1, "Ersetzt durch die Minuten der gesamten Spielzeit des Spielers", (player) -> String.format("%02d", (player.getStats().getOnlineTimeTotal() / 60) % 60));
        new VaroPlayerMessagePlaceholder("onlineTimeTotalSec", 1, "Ersetzt durch die Sekunden der gesamten Spielzeit des Spielers", (player) -> String.format("%02d", player.getStats().getOnlineTimeTotal() % 60));
        
        new VaroPlayerMessagePlaceholder("formattedCountdown", 1, "Ersetzt durch den formatierten Countdown des Spielers.", (player) -> {
            int secs = player.getStats().getCountdown();
            int hours = secs / 3600;
            String minsSecs = String.format("%s%02d%s:%s%02d%s", Main.getColorCode(), (secs / 60) % 60, ChatColor.GRAY, Main.getColorCode(), secs % 60, ChatColor.GRAY);

            return (hours >= 1) ? String.format("%s%02d%s:%s", Main.getColorCode(), hours, ChatColor.GRAY, minsSecs) : minsSecs;
        });

        new VaroPlayerMessagePlaceholder("hearts", 1, "Ersetzt durch die Leben des Spielers", (player) -> player.isOnline() ? String.valueOf((int) player.getPlayer().getHealth()) : "0");
        new VaroPlayerMessagePlaceholder("food", 1, "Ersetzt durch das Foodlevel des Spielers", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getFoodLevel()) : "0");
        new VaroPlayerMessagePlaceholder("kills", 1, "Ersetzt durch Kills des Spielers", (player) -> String.valueOf(player.getStats().getKills()));
        new VaroPlayerMessagePlaceholder("strikes", 1, "Ersetzt durch die Strikes des Spielers", (player) -> String.valueOf(player.getStats().getStrikes().size()));
        new VaroPlayerMessagePlaceholder("teamKills", 2, "Ersetzt durch die Teamkills des Spielers", (player) -> String.valueOf(player.getTeam() != null ? player.getTeam().getKills() : "-"));
        new VaroPlayerMessagePlaceholder("teamLifes", 2, "Ersetzt durch die Teamleben des Spielers", (player) -> String.valueOf((player.getTeam() != null ? player.getTeam().getLifes() : "-")));
        new VaroPlayerMessagePlaceholder("teamAlive", 1, "Ersetzt durch den Wahrheitswert, ob das Team lebt", (player) -> {
            if (player.getTeam() != null)
                return String.valueOf(player.getTeam().getMember().stream().anyMatch(p -> !p.equals(player) && p.getStats().isAlive()));
            else return "-";
        });

        new VaroPlayerMessagePlaceholder("teamMates", 1, "Ersetzt durch eine Liste der Mitspieler", (player) -> {
            if (player.getTeam() != null)
                return player.getTeam().getMember().stream().filter(p -> !p.equals(player)).map(VaroPlayer::getName).collect(Collectors.joining(", "));
            else return "-";
        });

        new VaroPlayerMessagePlaceholder("player", 1, "Ersetzt durch den Namen des Spielers", VaroPlayer::getName);
        new VaroPlayerMessagePlaceholder("playerID", 1, "Ersetzt durch die ID des Spielers", (player) -> String.valueOf(player.getId()));
        new VaroPlayerMessagePlaceholder("playerUUID", 1, "Ersetzt durch die UUID des Spielers", (player) -> String.valueOf(player.getUUID()));
        new VaroPlayerMessagePlaceholder("prefix", 1, "Ersetzt durch den Prefix des Spielers", VaroPlayer::getPrefix);
        new VaroPlayerMessagePlaceholder("team", 1, "Ersetzt durch den Teamname des Spielers", (player) -> player.getTeam() != null ? player.getTeam().getDisplay() : "-");
        new VaroPlayerMessagePlaceholder("teamID", 1, "Ersetzt durch die TeamID des Spielers", (player) -> player.getTeam() != null ? String.valueOf(player.getTeam().getId()) : "-");
        new VaroPlayerMessagePlaceholder("rank", 1, "Ersetzt durch den Rangnamen des Spielers", (player) -> player.getRank() != null ? player.getRank().getDisplay() : "-");
        new VaroPlayerMessagePlaceholder("episodesPlayed", 1, "Ersetzt durch die gespielten Episoden des Spielers", (player) -> String.valueOf(player.getStats().getSessionsPlayed()));
        new VaroPlayerMessagePlaceholder("episodesPlayedPlus1", 1, "Ersetzt durch die gespielten Episoden+1 des Spielers", (player) -> String.valueOf(player.getStats().getSessionsPlayed() + 1));
        new VaroPlayerMessagePlaceholder("sessions", 1, "Ersetzt durch die Sessions des Spielers", (player) -> String.valueOf(player.getStats().getSessions()));
        new VaroPlayerMessagePlaceholder("remainingDisconnects", 1, "Ersetzt durch die Disconnects der Session des Spielers", (player) -> String.valueOf(VaroPlayerDisconnect.getDisconnect(player.getPlayer()) != null ? ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt() - VaroPlayerDisconnect.getDisconnect(player.getPlayer()).getDisconnects() : ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt()));
        new VaroPlayerMessagePlaceholder("ping", 1, "Ersetzt durch den Ping des Spielers", (player) -> String.valueOf(player.getVersionAdapter().getPing()));
        new VaroPlayerMessagePlaceholder("pexPrefix", 10, "Ersetzt durch den Pex-Prefix des Spielers", PermissionUtils::getPermissionsExPrefix);
        new VaroPlayerMessagePlaceholder("worldSpawnX", 1, "Ersetzt durch die X-Koord des Spawns der Welt", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockX()) : "0");
        new VaroPlayerMessagePlaceholder("worldSpawnY", 1, "Ersetzt durch die Y-Koord des Spawns der Welt", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockY()) : "0");
        new VaroPlayerMessagePlaceholder("worldSpawnZ", 1, "Ersetzt durch die Z-Koord des Spawns der Welt", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockZ()) : "0");
        new VaroPlayerMessagePlaceholder("playerWorld", 1, "Ersetzt durch den Weltnamen des Spielers", (player) -> player.isOnline() ? player.getPlayer().getWorld().getName() : "NOWHERE");
        new VaroPlayerMessagePlaceholder("playerLocX", 1, "Ersetzt durch die X-Koordinate des Spielers", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockX()) : "0");
        new VaroPlayerMessagePlaceholder("playerLocY", 1, "Ersetzt durch die Y-Koordinate des Spielers", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockY()) : "0");
        new VaroPlayerMessagePlaceholder("playerLocZ", 1, "Ersetzt durch die Z-Koordinate des Spielers", (player) -> player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockZ()) : "0");
        new VaroPlayerMessagePlaceholder("lpPrefix", 10, "Ersetzt durch den LuckPerms-Prefix des Spielers", PermissionUtils::getLuckPermsPrefix);
    }
}