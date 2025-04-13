/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.cuuky.varo.config.language;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.function.Function;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Contexts.BorderDecreaseContext;
import de.cuuky.varo.config.language.Contexts.ContainerContext;
import de.cuuky.varo.config.language.Contexts.DeathContext;
import de.cuuky.varo.config.language.Contexts.KillContext;
import de.cuuky.varo.config.language.Contexts.OnlinePlayerContext;
import de.cuuky.varo.config.language.Contexts.PlayerContext;
import de.cuuky.varo.config.language.Contexts.StrikeContext;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.VaroPlayerDisconnect;
import de.cuuky.varo.spigot.VaroUpdateResultSet.UpdateResult;
import de.cuuky.varo.utils.VaroUtils;
import io.github.almightysatan.slams.PlaceholderResolver;

public final class Placeholders {

    static PlaceholderResolver getPlaceholders() {
        PlaceholderResolver.Builder builder = PlaceholderResolver.builder().builtIn()
                // Plugin
                .constant("varo-author", Main.getInstance().getDescription().getAuthors().get(0))
                .constant("varo-contributors", Main.getContributors())
                .constant("varo-version", Main.getInstance().getDescription().getVersion())
                .variable("varo-version-latest", () -> Main.getVaroUpdater() != null && Main.getVaroUpdater().getLastResult() != null && Main.getVaroUpdater().getLastResult().getUpdateResult() == UpdateResult.UPDATE_AVAILABLE ? Main.getVaroUpdater().getLastResult().getVersionName() : Main.getInstance().getDescription().getVersion())
                .constant("varo-discord", Main.DISCORD_INVITE)
                // Project
                .constant("projectname", Main.getProjectName())
                .constant("prefix", Main.getPrefix())
                .constant("colorcode", Main.getColorCode())
                .constant("discord", ConfigSetting.DISCORDBOT_INVITELINK.getValueAsString())
                .variable("num-players", () -> String.valueOf(VaroPlayer.getVaroPlayers().size()))
                .variable("num-alive", () -> String.valueOf(VaroPlayer.getAlivePlayer().size()))
                .variable("num-online", () -> String.valueOf(VaroPlayer.getOnlineAndAlivePlayer().size()))
                .variable("project-hour", () -> toPaddedString(Main.getVaroGame().getProjectTime() / 3600))
                .variable("project-minute", () -> toPaddedString((Main.getVaroGame().getProjectTime() / 60) % 60))
                .variable("project-second", () -> toPaddedString(Main.getVaroGame().getProjectTime() % 60))
                .variable("border-size", () -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getBorderSize(null)))
                .variable("border-radius", () -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getBorderRadius(null)))
                .variable("spawn-world", () -> Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getName())
                .withArgs("top-player", args -> {
                    if (args.size() == 0 || args.size() > 2) return "INVALID_ARGS";
                    try {
                        VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(Integer.parseInt(args.get(0)));
                        if (player != null)
                            return player.getName();
                        return args.size() == 1 ? "-" : args.get(1);
                    } catch (NumberFormatException e) {
                        return "INVALID_ARGS";
                    }
                })
                // Misc
                .constant("heart", "♥")
                .constant("newline", "\n")
                .withArgs("padding", args -> {
                    if (args.size() != 1) return "INVALID_ARGS";
                    try {
                        return String.join("", Collections.nCopies(Integer.parseInt(args.get(0)), " "));
                    } catch (NumberFormatException e) {
                        return "INVALID_ARGS";
                    }
                })
                .variable("year", () -> String.valueOf(LocalDate.now().getYear()))
                .variable("month", () -> toPaddedString(LocalDate.now().getMonthValue()))
                .variable("day", () -> toPaddedString(LocalDate.now().getDayOfMonth()))
                .variable("hour", () -> toPaddedString(LocalDateTime.now().getHour()))
                .variable("minute", () -> toPaddedString(LocalDateTime.now().getMinute()))
                .variable("second", () -> toPaddedString(LocalDateTime.now().getSecond()))
                .namespace(null, PlayerContext.class, Function.identity(), Placeholders::addPlayerPlaceholders)
                .namespace("killer-", KillContext.class, ctx -> new PlayerContext(ctx.getKiller()), Placeholders::addPlayerPlaceholders)
                .namespace("owner-", ContainerContext.class, ctx -> new PlayerContext(ctx.getOwner()), Placeholders::addPlayerPlaceholders)
                .contextual("death-reason", DeathContext.class, DeathContext::getReason)
                .contextual("strike-reason", StrikeContext.class, StrikeContext::getReason)
                .contextual("strike-operator", StrikeContext.class, StrikeContext::getOperator)
                .contextual("strike-num", StrikeContext.class, StrikeContext::getNum)
                .contextual("border-decrease-size", BorderDecreaseContext.class, BorderDecreaseContext::getSize)
                .contextual("border-decrease-speed", BorderDecreaseContext.class, BorderDecreaseContext::getSize)
                .contextual("border-decrease-time", BorderDecreaseContext.class, BorderDecreaseContext::getSize);
        
        for (ConfigSetting setting : ConfigSetting.values())
            if (!setting.isSensitive())
                builder.variable("config-" + setting.getFullPath().replace('.', '-'), () -> String.valueOf(setting.getValue()));
        return builder.build();
    }

    private static void addPlayerPlaceholders(PlaceholderResolver.Builder builder) {
        builder.contextual("name", PlayerContext.class, (ctx) -> ctx.getPlayer().getName())
        .contextual("displayname", PlayerContext.class, (ctx) -> ctx.getPlayer().getDisplayName())
        .contextual("uuid", PlayerContext.class, (ctx) -> ctx.getPlayer().getUUID())
        .contextual("id", PlayerContext.class, (ctx) -> String.valueOf(ctx.getPlayer().getId()))
        .contextual("team", PlayerContext.class, (ctx) -> ctx.getPlayer().getTeam() != null ? ctx.getPlayer().getTeam().getDisplay() : "-")
        .contextual("team-id", PlayerContext.class, (ctx) -> ctx.getPlayer().getTeam() != null ? String.valueOf(ctx.getPlayer().getTeam().getId()) : "-")
        .contextual("team-lives", PlayerContext.class, ctx -> ctx.getPlayer().getTeam() != null ? String.valueOf(ctx.getPlayer().getTeam().getLifes()) : "-")
        .contextual("team-kills", PlayerContext.class, ctx -> ctx.getPlayer().getTeam() != null ? String.valueOf(ctx.getPlayer().getTeam().getKills()) : "-")
        .contextual("rank", PlayerContext.class, (ctx) -> ctx.getPlayer().getRank() != null ? ctx.getPlayer().getRank().getDisplay() : "-")
        .contextual("kills", PlayerContext.class, (ctx) -> String.valueOf(ctx.getPlayer().getStats().getKills()))
        .contextual("strikes", PlayerContext.class, (ctx) -> String.valueOf(ctx.getPlayer().getStats().getStrikes().size()))
        .contextual("countdown-hour", PlayerContext.class, (ctx) -> !Main.getVaroGame().isPlayTimeLimited() ? "-" : toPaddedString(ctx.getPlayer().getStats().getCountdown() / 3600))
        .contextual("countdown-minute", PlayerContext.class, (ctx) -> !Main.getVaroGame().isPlayTimeLimited() ? "-" : toPaddedString((ctx.getPlayer().getStats().getCountdown() / 60) % 60))
        .contextual("countdown-second", PlayerContext.class, (ctx) -> !Main.getVaroGame().isPlayTimeLimited() ? "-" : toPaddedString(ctx.getPlayer().getStats().getCountdown() % 60))
        .contextual("session-hour", PlayerContext.class, (ctx) -> toPaddedString(ctx.getPlayer().getStats().getSessionTime() / 3600))
        .contextual("session-minute", PlayerContext.class, (ctx) -> toPaddedString((ctx.getPlayer().getStats().getSessionTime() / 60) % 60))
        .contextual("session-second", PlayerContext.class, (ctx) -> toPaddedString(ctx.getPlayer().getStats().getSessionTime() % 60))
        .contextual("online-hour", PlayerContext.class, (ctx) -> toPaddedString(ctx.getPlayer().getStats().getOnlineTime() / 3600))
        .contextual("online-minute", PlayerContext.class, (ctx) -> toPaddedString((ctx.getPlayer().getStats().getOnlineTime() / 60) % 60))
        .contextual("online-second", PlayerContext.class, (ctx) -> toPaddedString(ctx.getPlayer().getStats().getOnlineTime() % 60))
        .contextual("online-total-hour", PlayerContext.class, (ctx) -> toPaddedString(ctx.getPlayer().getStats().getOnlineTimeTotal() / 3600))
        .contextual("online-total-minute", PlayerContext.class, (ctx) -> toPaddedString((ctx.getPlayer().getStats().getOnlineTimeTotal() / 60) % 60))
        .contextual("online-total-second", PlayerContext.class, (ctx) -> toPaddedString(ctx.getPlayer().getStats().getOnlineTimeTotal() % 60))
        .contextual("episode", PlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getStats().getSessionsPlayed()))
        .contextual("episodes-remaining", PlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getStats().getSessions()))
        .contextual("remaining-disconnects", PlayerContext.class, ctx -> String.valueOf(VaroPlayerDisconnect.getDisconnect(ctx.getPlayer().getPlayer()) != null ? ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt() - VaroPlayerDisconnect.getDisconnect(ctx.getPlayer().getPlayer()).getDisconnects() : ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt()))
        .contextual("luckperms-prefix", PlayerContext.class, ctx -> VaroUtils.getLuckPermsPrefix(ctx.getPlayer()))
        .contextual("luckperms-suffix", PlayerContext.class, ctx -> VaroUtils.getLuckPermsSuffix(ctx.getPlayer()))
        
        .conditional("online", PlayerContext.class, ctx -> ctx.getPlayer() != null, (ctx, args) -> args.size() > 1 ? args.get(1) : "")
        .namespace(null, PlayerContext.class, PlayerContext::toOnlinePlayerContext, onlineBuilder -> {
            onlineBuilder.contextual("ping", OnlinePlayerContext.class, ctx -> String.valueOf(VaroPlayer.getPlayer(ctx.getPlayer()).getVersionAdapter().getPing()))
            .contextual("x", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getBlockX()))
            .contextual("y", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getBlockY()))
            .contextual("z", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getBlockZ()))
            .contextual("world", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getWorld().getName())
            .contextual("distance-to-border", OnlinePlayerContext.class, ctx -> String.valueOf((int) Main.getVaroGame().getVaroWorldHandler().getVaroWorld(ctx.getPlayer().getWorld()).getVaroBorder().getDistance(ctx.getPlayer())))
            .contextual("spawn-x", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getWorld().getSpawnLocation().getBlockX()), (ctx, args) -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockX()))
            .contextual("spawn-y", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getWorld().getSpawnLocation().getBlockY()), (ctx, args) -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockY()))
            .contextual("spawn-z", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getWorld().getSpawnLocation().getBlockZ()), (ctx, args) -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockZ()))
            .contextual("spawn-distance", OnlinePlayerContext.class, ctx -> String.valueOf((int) ctx.getPlayer().getLocation().distance(ctx.getPlayer().getLocation().getWorld().getSpawnLocation())))
            .contextual("health", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getHealth()))
            .contextual("food", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getFoodLevel()));
        });
    }
    
    private static String toPaddedString(long value) {
        return value <= 9 ? "0" + value : String.valueOf(value);
    }
}
