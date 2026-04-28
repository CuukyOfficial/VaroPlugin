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

package de.varoplugin.varo.config.language;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Contexts.*;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.VaroPlayerDisconnect;
import de.varoplugin.varo.spigot.VaroUpdateResultSet.UpdateResult;
import de.varoplugin.varo.team.VaroTeam;
import de.varoplugin.varo.utils.VaroUtils;
import io.github.almightysatan.slams.Component;
import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.PlaceholderResolver;
import io.github.almightysatan.slams.PlaceholderResolver.Builder;
import io.github.almightysatan.slams.bukkit.BukkitPlaceholders;
import io.github.almightysatan.slams.papi.PapiPlaceholders;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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
        .variable("num-players", () -> VaroPlayer.getVaroPlayers().size())
        .variable("num-alive", () -> VaroPlayer.getAlivePlayer().size())
        .variable("num-online", () -> VaroPlayer.getOnlineAndAlivePlayer().size())
        .variable("project-hour", () -> toPaddedString(Main.getVaroGame().getProjectTime() / 3600))
        .variable("project-minute", () -> toPaddedString((Main.getVaroGame().getProjectTime() / 60) % 60))
        .variable("project-second", () -> toPaddedString(Main.getVaroGame().getProjectTime() % 60))
        .variable("border-size", () -> (long) Main.getVaroGame().getVaroWorldHandler().getBorderSize(null))
        .variable("border-radius", () -> (long) Main.getVaroGame().getVaroWorldHandler().getBorderRadius(null))
        .variable("spawn-world", () -> Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getName())
        .withArgs("top-player", args -> { // top-player-name should be used instead
            if (args.isEmpty() || args.size() > 2) return Placeholder.INVALID_ARGUMENTS;
            try {
                int index = Integer.parseInt(args.get(0));
                if (index <= 0)
                    return Placeholder.INVALID_ARGUMENTS;
                VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(index);
                if (player != null)
                    return player.getName();
                return args.size() == 1 ? "-" : args.get(1);
            } catch (NumberFormatException e) {
                return Placeholder.INVALID_ARGUMENTS;
            }
        })
        // Misc
        .constant("heart", "♥")
        .constant("newline", "\n")
        .withArgs("padding", args -> {
            if (args.size() != 1) return Placeholder.INVALID_ARGUMENTS;
            try {
                return String.join("", Collections.nCopies(Integer.parseInt(args.get(0)), " "));
            } catch (NumberFormatException e) {
                return Placeholder.INVALID_ARGUMENTS;
            }
        })
        .variable("year", () -> LocalDate.now().getYear())
        .variable("month", () -> toPaddedString(LocalDate.now().getMonthValue()))
        .variable("day", () -> toPaddedString(LocalDate.now().getDayOfMonth()))
        .variable("hour", () -> toPaddedString(LocalDateTime.now().getHour()))
        .variable("minute", () -> toPaddedString(LocalDateTime.now().getMinute()))
        .variable("second", () -> toPaddedString(LocalDateTime.now().getSecond()))
        .withArgs("pad-left", args -> {
            if (args.size() != 3)
                return Placeholder.INVALID_ARGUMENTS;
            try {
                return StringUtils.leftPad(args.get(0), Integer.parseInt(args.get(1)), args.get(2));
            } catch (NumberFormatException e) {
                return Placeholder.INVALID_ARGUMENTS;
            }
        })
        .withArgs("pad-right", args -> {
            if (args.size() != 3)
                return Placeholder.INVALID_ARGUMENTS;
            try {
                return StringUtils.rightPad(args.get(0), Integer.parseInt(args.get(1)), args.get(2));
            } catch (NumberFormatException e) {
                return Placeholder.INVALID_ARGUMENTS;
            }
        })
        // Player
        .namespace(null, PlayerContext.class, Function.identity(), Placeholders::addPlayerPlaceholders)
        .namespace("killer-", KillContext.class, ctx -> new PlayerContext(ctx.getKiller()), Placeholders::addPlayerPlaceholders)
        .namespace("owner-", ContainerContext.class, ctx -> new PlayerContext(ctx.getOwner()), Placeholders::addPlayerPlaceholders)
        // Other contexts
        .contextual("death-reason", DeathContext.class, DeathContext::getReason)
        .contextual("strike-reason", StrikeContext.class, StrikeContext::getReason)
        .contextual("strike-operator", StrikeContext.class, StrikeContext::getOperator)
        .contextual("strike-num", StrikeContext.class, StrikeContext::getNum)
        .contextual("border-decrease-size", BorderDecreaseContext.class, BorderDecreaseContext::getSize)
        .contextual("border-decrease-speed", BorderDecreaseContext.class, BorderDecreaseContext::getSpeed)
        .contextual("border-decrease-time", BorderDecreaseContext.class, BorderDecreaseContext::getTime);
        
        addPlayerPlaceholders(topBuilder(builder, "top-player-", index -> {
            VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(index);
            if (player == null)
                return null;
            return new PlayerContext(player);
        }));
        addPlayerPlaceholders(topBuilder(builder, "top-team-", index -> {
            VaroTeam team = Main.getVaroGame().getTopScores().getTeam(index);
            if (team == null)
                return null;
            return new TeamContext(team);
        }));

        for (ConfigSetting setting : ConfigSetting.values())
            if (!setting.isSensitive())
                builder.variable("config-" + setting.getFullPath().replace('.', '-'), setting::getValue);

        BukkitPlaceholders.addBuiltIn(builder);
        PapiPlaceholders.addIfAvailable(builder);

        return builder.build();
    }

    private static void addPlayerPlaceholders(PlaceholderResolver.Builder builder) {
        builder.contextual("name", PlayerContext.class, (ctx) -> ctx.getPlayer().getName())
        .contextual("displayname", PlayerContext.class, (ctx) -> ctx.getPlayer().getDisplayName())
        .contextual("uuid", PlayerContext.class, (ctx) -> ctx.getPlayer().getUUID())
        .contextual("id", PlayerContext.class, (ctx) -> ctx.getPlayer().getId())
        .contextual("rank", PlayerContext.class, (ctx) -> ctx.getPlayer().getRank() != null ? ctx.getPlayer().getRank().getDisplay() : "-")
        .contextual("kills", PlayerContext.class, (ctx) -> ctx.getPlayer().getStats().getKills())
        .contextual("strikes", PlayerContext.class, (ctx) -> ctx.getPlayer().getStats().getStrikes().size())
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
        .contextual("episode", PlayerContext.class, ctx -> ctx.getPlayer().getStats().getSessionsPlayed())
        .contextual("episodes-remaining", PlayerContext.class, ctx -> ctx.getPlayer().getStats().getSessions())
        .contextual("remaining-disconnects", PlayerContext.class, ctx -> VaroPlayerDisconnect.getDisconnect(ctx.getPlayer().getPlayer()) != null ? ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt() - VaroPlayerDisconnect.getDisconnect(ctx.getPlayer().getPlayer()).getDisconnects() : ConfigSetting.DISCONNECT_PER_SESSION.getValueAsInt())
        .contextual("luckperms-prefix", PlayerContext.class, ctx -> VaroUtils.getLuckPermsPrefix(ctx.getPlayer()))
        .contextual("luckperms-suffix", PlayerContext.class, ctx -> VaroUtils.getLuckPermsSuffix(ctx.getPlayer()))
        .contextual("blocklogger", PlayerContext.class, (ctx, args) -> {
            if (args.size() != 1)
                return Placeholder.INVALID_ARGUMENTS;
            return Main.getDataManager().getVaroLoggerManager().getBlockLogger().getUuidMaterialLogs(ctx.getPlayer().getUUID(), args.get(0)).size();
        })
        // Online
        .conditional("online", PlayerContext.class, ctx -> ctx.getPlayer().getPlayer() != null, (ctx, args) -> args.size() > 1 ? args.get(1) : "")
        .namespace(null, PlayerContext.class, PlayerContext::toOnlinePlayerContext, onlineBuilder -> {
            onlineBuilder.contextual("ping", OnlinePlayerContext.class, ctx -> VaroPlayer.getPlayer(ctx.getPlayer()).getVersionAdapter().getPing())
            .contextual("x", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getBlockX())
            .contextual("y", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getBlockY())
            .contextual("z", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getBlockZ())
            .contextual("world", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getWorld().getName())
            .contextual("distance-to-border", OnlinePlayerContext.class, ctx -> (int) Main.getVaroGame().getVaroWorldHandler().getVaroWorld(ctx.getPlayer().getWorld()).getVaroBorder().getDistance(ctx.getPlayer()))
            .contextual("spawn-x", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getWorld().getSpawnLocation().getBlockX(), (ctx, args) -> Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockX())
            .contextual("spawn-y", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getWorld().getSpawnLocation().getBlockY(), (ctx, args) -> Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockY())
            .contextual("spawn-z", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getWorld().getSpawnLocation().getBlockZ(), (ctx, args) -> Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockZ())
            .contextual("spawn-distance", OnlinePlayerContext.class, ctx -> (int) ctx.getPlayer().getLocation().distance(ctx.getPlayer().getLocation().getWorld().getSpawnLocation()))
            .contextual("health", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getHealth())
            .contextual("food", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getFoodLevel());
        })
        // Team
        .conditional("has-team", PlayerContext.class, ctx -> ctx.getPlayer().getTeam() != null, (ctx, args) -> args.size() > 1 ? args.get(1) : "")
        .namespace("team-", PlayerContext.class, PlayerContext::toTeamContext, Placeholders::addTeamPlaceholders);
    }
    
    private static void addTeamPlaceholders(PlaceholderResolver.Builder builder) {
        builder.contextual("name", TeamContext.class, (ctx) -> ctx.getTeam().getName())
        .contextual("displayname", TeamContext.class, (ctx) -> ctx.getTeam().getDisplayName())
        .contextual("id", TeamContext.class, (ctx) -> ctx.getTeam().getId())
        .contextual("kills", TeamContext.class, (ctx) -> ctx.getTeam().getKills())
        .contextual("lives", TeamContext.class, (ctx) -> ctx.getTeam().getLives().toPlainString());
    }
    
    private static String toPaddedString(long value) {
        return value <= 9 ? "0" + value : String.valueOf(value);
    }
    
    private static Builder topBuilder(Builder builder, String prefix, Function<Integer, Object> function) {
        return new Builder() {

            @Override
            public @NotNull PlaceholderResolver build() {
                throw new UnsupportedOperationException();
            }

            @Override
            public @NotNull Builder add(@NotNull Placeholder placeholder) {
                builder.add(new Placeholder() {
                    @Override
                    public @NotNull String key() {
                        return prefix + placeholder.key();
                    }

                    @Override
                    public boolean constexpr() {
                        return false;
                    }

                    @Override
                    public @NotNull <T> Component<T> value(@NotNull Object @NotNull [] contexts, @Unmodifiable @NotNull List<@NotNull Argument<T>> arguments, Component.@NotNull ValueFactory<T> factory) {
                        if (arguments.size() < 2)
                            return factory.component(Placeholder.INVALID_ARGUMENTS);
                        try {
                            int index = Integer.parseInt(arguments.get(0).stringValue());
                            if (index <= 0)
                                return factory.component(Placeholder.INVALID_ARGUMENTS);
                            Object context = function.apply(index);
                            if (context == null)
                                return arguments.get(1);
                            return placeholder.value(new Object[]{context}, arguments.subList(2, arguments.size()), factory);
                        } catch (NumberFormatException e) {
                            return factory.component(Placeholder.INVALID_ARGUMENTS);
                        }
                    }

                    @Override
                    public <T> @NonNull ProcessedPlaceholder<T> processArguments(@Unmodifiable @NotNull List<@Nullable Argument<T>> arguments, Component.@NotNull ValueFactory<T> factory) {
                        if (arguments.size() < 2)
                            return factory.processedPlaceholder(Placeholder.INVALID_ARGUMENTS);
                        
                        Argument<T> arg0 = arguments.get(0);
                        try {
                            Integer index = arg0 != null ? Integer.parseInt(arg0.stringValue()) : null;
                            if (index != null && index <= 0)
                                return factory.processedPlaceholder(Placeholder.INVALID_ARGUMENTS);
                            
                            return (contexts0, arguments0, factory0) -> {
                                int i;
                                try {
                                    i = index != null ? index : Integer.parseInt(arguments0.get(0).stringValue());
                                } catch (NumberFormatException e) {
                                    return factory0.component(Placeholder.INVALID_ARGUMENTS);
                                }
                                
                                Object context = function.apply(i);
                                if (context == null)
                                    return arguments0.get(1);
                                return placeholder.value(new Object[]{context}, arguments0.subList(2, arguments0.size()), factory0);
                            };
                        } catch (NumberFormatException e) {
                            return factory.processedPlaceholder(Placeholder.INVALID_ARGUMENTS);
                        }
                    }
                });
                return this;
            }
        };
    }
}
