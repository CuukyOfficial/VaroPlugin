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

import java.util.function.Function;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Contexts.KillerContext;
import de.cuuky.varo.config.language.Contexts.OnlinePlayerContext;
import de.cuuky.varo.config.language.Contexts.PlayerContext;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import io.github.almightysatan.slams.PlaceholderResolver;

public final class Placeholders {

    static PlaceholderResolver getPlaceholders() {
        PlaceholderResolver.Builder builder = PlaceholderResolver.builder().builtIn()
                // Plugin
                .constant("varo-author", Main.getInstance().getDescription().getAuthors().get(0))
                .constant("varo-contributors", Main.getContributors())
                .constant("varo-version", Main.getInstance().getDescription().getVersion())
                .constant("varo-discord", Main.DISCORD_INVITE)
                // Project
                .constant("projectname", Main.getProjectName())
                .constant("colorcode", Main.getColorCode())
                .constant("discord", ConfigSetting.DISCORDBOT_INVITELINK.getValueAsString())
                .variable("registered", () -> "TODO")
                .variable("remaining", () -> "TODO")
                .variable("online", () -> "TODO")
                .variable("project-hour", () -> String.format("%02d", Main.getVaroGame().getProjectTime() / 3600))
                .variable("project-minute", () -> String.format("%02d", (Main.getVaroGame().getProjectTime() / 60) % 60))
                .variable("project-second", () -> String.format("%02d", Main.getVaroGame().getProjectTime() % 60))
                .variable("border-size", () -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getBorderSize(null)))
                .constant("protection-time", String.valueOf(ConfigSetting.JOIN_PROTECTIONTIME.getValueAsInt()))
                .variable("spawn-x", () -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockX()))
                .variable("spawn-y", () -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockY()))
                .variable("spawn-z", () -> String.valueOf(Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getSpawnLocation().getBlockZ()))
                .variable("spawn-world", () -> Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld().getName())
                // Misc
                .constant("heart", "♥")
                .constant("newline", "\n")
                .variable("year", () -> "TODO")
                .variable("month", () -> "TODO")
                .variable("day", () -> "TODO")
                .variable("hour", () -> "TODO")
                .variable("minute", () -> "TODO")
                .variable("second", () -> "TODO")
                .namespace(null, PlayerContext.class, Function.identity(), Placeholders::addPlayerPlaceholders)
                .namespace("killer-", KillerContext.class, ctx -> new PlayerContext(ctx.getPlayer()), Placeholders::addPlayerPlaceholders);
        for (ConfigSetting setting : ConfigSetting.values())
            if (!setting.isSensitive())
                builder.variable("config-" + setting.getPath().replace('.', '-'), () -> String.valueOf(setting.getValue()));
        return builder.build();
    }

    private static void addPlayerPlaceholders(PlaceholderResolver.Builder builder) {
        builder.contextual("name", PlayerContext.class, (ctx) -> ctx.getPlayer().getName());
        builder.contextual("uuid", PlayerContext.class, (ctx) -> ctx.getPlayer().getUUID());
        builder.contextual("id", PlayerContext.class, (ctx) -> String.valueOf(ctx.getPlayer().getId()));
        builder.contextual("prefix", PlayerContext.class, (ctx) -> ctx.getPlayer().getPrefix());
        builder.contextual("team", PlayerContext.class, (ctx) -> ctx.getPlayer().getTeam() != null ? ctx.getPlayer().getTeam().getDisplay() : "-");
        builder.contextual("team-id", PlayerContext.class, (ctx) -> ctx.getPlayer().getTeam() != null ? String.valueOf(ctx.getPlayer().getTeam().getId()) : "-");
        builder.contextual("rank", PlayerContext.class, (ctx) -> ctx.getPlayer().getRank() != null ? ctx.getPlayer().getRank().getDisplay() : "-");
        builder.namespace(null, PlayerContext.class, PlayerContext::toOnlinePlayerContext, onlineBuilder -> {
            onlineBuilder.contextual("x", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getBlockX()));
            onlineBuilder.contextual("y", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getBlockY()));
            onlineBuilder.contextual("z", OnlinePlayerContext.class, ctx -> String.valueOf(ctx.getPlayer().getLocation().getBlockZ()));
            onlineBuilder.contextual("world", OnlinePlayerContext.class, ctx -> ctx.getPlayer().getLocation().getWorld().getName());
        });
    }
}
