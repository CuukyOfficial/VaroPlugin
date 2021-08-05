package de.cuuky.varo.configuration.placeholder;

import java.util.stream.Collectors;

import de.cuuky.cfw.configuration.placeholder.placeholder.util.DateInfo;
import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.cfw.utils.PermissionUtils;
import de.cuuky.cfw.version.BukkitVersion;
import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.placeholder.varo.VaroGeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.varo.VaroPlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.disconnect.VaroPlayerDisconnect;
import org.bukkit.ChatColor;

public class MessagePlaceholderLoader {

    public MessagePlaceholderLoader() {
        loadMessagePlaceHolder();
        loadPlayerPlaceholder();
    }

    private void loadMessagePlaceHolder() {
        new VaroGeneralMessagePlaceholder("pluginName", -1, "Ersetzt durch den Plugin-Namen") {

            @Override
            protected String getValue() {
                return Main.getInstance().getDescription().getName();
            }
        };

        new VaroGeneralMessagePlaceholder("pluginAuthor", -1, "Ersetzt durch den Plugin-Macher") {

            @Override
            protected String getValue() {
                return Main.getInstance().getDescription().getAuthors().get(0);
            }
        };

        new VaroGeneralMessagePlaceholder("pluginVersion", -1, "Ersetzt durch die Plugin-Version") {

            @Override
            protected String getValue() {
                return Main.getInstance().getDescription().getVersion();
            }
        };

        new VaroGeneralMessagePlaceholder("projectname", 10, "Ersetzt durch den Projektnamen") {

            @Override
            protected String getValue() {
                return Main.getProjectName();
            }
        };

        new VaroGeneralMessagePlaceholder("colorcode", 10, "Ersetzt durch den Farbcode") {

            @Override
            protected String getValue() {
                return Main.getColorCode();
            }
        };

        new VaroGeneralMessagePlaceholder("remaining", 1, "Ersetzt durch die Anzahl lebender Spieler") {

            @Override
            protected String getValue() {
                return String.valueOf(VaroPlayer.getAlivePlayer().size());
            }
        };

        new VaroGeneralMessagePlaceholder("players", 1, "Ersetzt durch die Anzahl aller Spieler") {

            @Override
            protected String getValue() {
                return String.valueOf(VaroPlayer.getVaroPlayer().size());
            }
        };

        new VaroGeneralMessagePlaceholder("online", 1, "Ersetzt durch die Anzahl aller online Spieler") {

            @Override
            protected String getValue() {
                return String.valueOf(VersionUtils.getOnlinePlayer().size());
            }
        };

        new VaroGeneralMessagePlaceholder("currYear", 1, "Ersetzt durch das Jahr der jetzigen Zeit") {

            @Override
            protected String getValue() {
                return getLastDateRefresh(DateInfo.YEAR);
            }
        };

        new VaroGeneralMessagePlaceholder("currMonth", 1, "Ersetzt durch den Monat der jetzigen Zeit") {

            @Override
            protected String getValue() {
                return getLastDateRefresh(DateInfo.MONTH);
            }
        };

        new VaroGeneralMessagePlaceholder("currDay", 1, "Ersetzt durch den Tag der jetzigen Zeit") {

            @Override
            protected String getValue() {
                return getLastDateRefresh(DateInfo.DAY);
            }
        };

        new VaroGeneralMessagePlaceholder("currHour", 1, "Ersetzt durch die Stunde der jetzigen Zeit") {

            @Override
            protected String getValue() {
                return getLastDateRefresh(DateInfo.HOUR);
            }
        };

        new VaroGeneralMessagePlaceholder("currMin", 1, "Ersetzt durch die Minute der jetzigen Zeit") {

            @Override
            protected String getValue() {
                return getLastDateRefresh(DateInfo.MINUTES);
            }
        };

        new VaroGeneralMessagePlaceholder("currSec", 1, "Ersetzt durch die Sekunden der jetzigen Zeit") {

            @Override
            protected String getValue() {
                return getLastDateRefresh(DateInfo.SECONDS);
            }
        };

        new VaroGeneralMessagePlaceholder("bordersize", 1, "Ersetzt durch die Bordergroesse") {

            @Override
            protected String getValue() {
                return !Main.isBootedUp() ? "0" : String.valueOf((int) Main.getVaroGame().getVaroWorldHandler().getBorderSize(null));
            }
        };

        new VaroGeneralMessagePlaceholder("discordLink", 20, "Ersetzt durch den DiscordLink aus der Config") {

            @Override
            protected String getValue() {
                return ConfigSetting.DISCORDBOT_INVITELINK.getValueAsString();
            }
        };

        new VaroGeneralMessagePlaceholder("protectionTime", 5, "Ersetzt durch die Schutzzeit aus der Config") {

            @Override
            protected String getValue() {
                return String.valueOf(ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt());
            }
        };

        new VaroGeneralMessagePlaceholder("&", -1, true, "Ersetzt durch §") {

            @Override
            protected String getValue() {
                return "§";
            }
        };

        new VaroGeneralMessagePlaceholder("heart", -1, "Ersetzt durch ♥") {

            @Override
            protected String getValue() {
                return "♥";
            }
        };

        new VaroGeneralMessagePlaceholder("nextLine", -1, "Fuegt neue Zeile ein") {

            @Override
            protected String getValue() {
                return "\n";
            }
        };

        new VaroGeneralMessagePlaceholder("null", -1, "Ersetzt durch nichts") {

            @Override
            protected String getValue() {
                return "";
            }
        };

        // MAYBE ?
        for (ConfigSetting setting : ConfigSetting.values()) {
            new VaroGeneralMessagePlaceholder(setting.getPath(), 10, JavaUtils.getArgsToString(setting.getDescription(), " ")) {

                @Override
                protected String getValue() {
                    return String.valueOf(setting.getValue()).replace("&", "§");
                }
            };
        }
    }

    private void loadPlayerPlaceholder() {
        new VaroPlayerMessagePlaceholder("distanceToBorder", 1, "Ersetzt durch die Distanz zur Border des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                if (!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
                    return "0";

                return String.valueOf((int) Main.getVaroGame().getVaroWorldHandler().getVaroWorld(player.getPlayer().getWorld()).getVaroBorder().getBorderDistanceTo(player.getPlayer()));
            }
        };

        new VaroPlayerMessagePlaceholder("min", 1, "Ersetzt durch die Minuten der Spielzeit des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return ConfigSetting.PLAY_TIME.getValueAsInt() < 1 ? "-" : player.getStats().getCountdownMin(player.getStats().getCountdown());
            }
        };

        new VaroPlayerMessagePlaceholder("sec", 1, "Ersetzt durch die Sekunden der Spielzeit des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return ConfigSetting.PLAY_TIME.getValueAsInt() < 1 ? "-" : player.getStats().getCountdownSec(player.getStats().getCountdown());
            }
        };

        new VaroPlayerMessagePlaceholder("hearts", 1, "Ersetzt durch die Leben des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(VersionUtils.getHearts(player.getPlayer())) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("food", 1, "Ersetzt durch das Foodlevel des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getFoodLevel()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("kills", 1, "Ersetzt durch Kills des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getStats().getKills());
            }
        };

        new VaroPlayerMessagePlaceholder("strikes", 1, "Ersetzt durch die Strikes des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getStats().getStrikes().size());
            }
        };

        new VaroPlayerMessagePlaceholder("teamKills", 2, "Ersetzt durch die Teamkills des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getTeam() != null ? player.getTeam().getKills() : "-");
            }
        };

        new VaroPlayerMessagePlaceholder("teamLifes", 2, "Ersetzt durch die Teamleben des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf((player.getTeam() != null ? player.getTeam().getLifes() : "-"));
            }
        };

        new VaroPlayerMessagePlaceholder("teamAlive", 1, "Ersetzt durch den Wahrheitswert, ob das Team lebt") {

            @Override
            protected String getValue(VaroPlayer player) {
                if (player.getTeam() != null)
                    return String.valueOf(player.getTeam().getMember().stream().filter(p -> !p.equals(player) && p.getStats().isAlive()).findFirst().isPresent());
                else
                    return "-";
            }
        };

        new VaroPlayerMessagePlaceholder("teamMates", 1, "Ersetzt durch eine Liste der Mitspieler") {

            @Override
            protected String getValue(VaroPlayer player) {
                if (player.getTeam() != null)
                    return player.getTeam().getMember().stream().filter(p -> !p.equals(player)).map(VaroPlayer::getName).collect(Collectors.joining(", "));
                else
                    return "-";
            }
        };

        new VaroPlayerMessagePlaceholder("player", 1, "Ersetzt durch den Namen des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.getName();
            }
        };

        new VaroPlayerMessagePlaceholder("playerID", 1, "Ersetzt durch die ID des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getId());
            }
        };

        new VaroPlayerMessagePlaceholder("playerUUID", 1, "Ersetzt durch die UUID des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getUUID());
            }
        };

        new VaroPlayerMessagePlaceholder("prefix", 1, "Ersetzt durch den Prefix des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.getPrefix();
            }
        };

        new VaroPlayerMessagePlaceholder("team", 1, "Ersetzt durch den Teamname des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.getTeam() != null ? player.getTeam().getDisplay() : "-";
            }
        };

        new VaroPlayerMessagePlaceholder("teamID", 1, "Ersetzt durch die TeamID des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.getTeam() != null ? String.valueOf(player.getTeam().getId()) : "-";
            }
        };

        new VaroPlayerMessagePlaceholder("rank", 1, "Ersetzt durch den Rangnamen des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.getRank() != null ? player.getRank().getDisplay() : "-";
            }
        };

        new VaroPlayerMessagePlaceholder("episodesPlayedPlus1", 1, "Ersetzt durch die gespielten Episoden+1 des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getStats().getSessionsPlayed() + 1);
            }
        };

        new VaroPlayerMessagePlaceholder("sessions", 1, "Ersetzt durch die Sessions des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getStats().getSessions());
            }
        };

        new VaroPlayerMessagePlaceholder("seconds", 1, "Ersetzt durch den Countdown des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getStats().getCountdown());
            }
        };

        new VaroPlayerMessagePlaceholder("countdown", 1, "Ersetzt durch den Countdown des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getStats().getCountdown());
            }
        };

        new VaroPlayerMessagePlaceholder("remainingDisconnects", 1, "Ersetzt durch die Disconnects der Session des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(VaroPlayerDisconnect.getDisconnect(player.getPlayer()) != null ? ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt() - VaroPlayerDisconnect.getDisconnect(player.getPlayer()).getDisconnects() : ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt());
            }
        };

        new VaroPlayerMessagePlaceholder("ping", 1, "Ersetzt durch den Countdown des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return String.valueOf(player.getVersionAdapter().getPing());
            }
        };

        new VaroPlayerMessagePlaceholder("pexPrefix", 10, "Ersetzt durch den Pex-Prefix des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return PermissionUtils.getPermissionsExPrefix(player);
            }
        };

        new VaroPlayerMessagePlaceholder("worldSpawnX", 1, "Ersetzt durch die X-Koord des Spawns der Welt") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockX()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("worldSpawnY", 1, "Ersetzt durch die Y-Koord des Spawns der Welt") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockY()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("worldSpawnZ", 1, "Ersetzt durch die Z-Koord des Spawns der Welt") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getWorld().getSpawnLocation().getBlockZ()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("playerWorld", 1, "Ersetzt durch den Weltnamen des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? player.getPlayer().getWorld().getName() : "NOWHERE";
            }
        };

        new VaroPlayerMessagePlaceholder("playerLocX", 1, "Ersetzt durch die X-Koordinate des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockX()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("playerLocY", 1, "Ersetzt durch die Y-Koordinate des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockY()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("playerLocZ", 1, "Ersetzt durch die Z-Koordinate des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return player.isOnline() ? String.valueOf(player.getPlayer().getLocation().getBlockZ()) : "0";
            }
        };

        new VaroPlayerMessagePlaceholder("lpPrefix", 10, "Ersetzt durch den LuckPerms-Prefix des Spielers") {

            @Override
            protected String getValue(VaroPlayer player) {
                return PermissionUtils.getLuckPermsPrefix(player);
            }
        };

        new VaroPlayerMessagePlaceholder("formattedCountdown", 1, "Ersetzt durch den formatierten Countdown des Spielers.") {

            @Override
            protected String getValue(VaroPlayer player) {
                int secs = player.getStats().getCountdown();
                int hours = secs / 3600;
                String minsSecs = String.format("%s%02d%s:%s%02d%s", Main.getColorCode(), (secs / 60) % 60, ChatColor.GRAY, Main.getColorCode(), secs % 60, ChatColor.GRAY);

                String msg = (hours >= 1) ? String.format("%s%02d%s:%s", Main.getColorCode(), hours, ChatColor.GRAY, minsSecs) : minsSecs;

                return msg;
            }
        };
    }
}
