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

package de.varoplugin.varo.bot.discord.commands;

import java.awt.Color;
import java.text.SimpleDateFormat;

import de.varoplugin.varo.bot.discord.DiscordBotCommand;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.Stats;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class PlayerCommand extends DiscordBotCommand {

    public PlayerCommand() {
        super("player", "Shows information about a player", new OptionData(OptionType.STRING, "player", "The name", true));
    }

    @Override
    public void onExecute(SlashCommandInteraction event) {
        VaroPlayer player = VaroPlayer.getPlayer(event.getOption("player").getAsString());
        if (player == null) {
            getDiscordBot().replyError("Unknown player!", event);
            return;
        }
        Stats stats = player.getStats();

        StringBuilder text = new StringBuilder();
        if (player.getTeam() != null)
            text.append("**Team**: ").append(player.getTeam().getName()).append("\n");
        text.append("**Sessions played**: ").append(stats.getSessionsPlayed());
        text.append("\n**Kills**: ").append(stats.getKills());
        text.append("\n**Strikes**: ").append(stats.getStrikes() != null ? stats.getStrikes().size() : "0");
        if (stats.isDead() && stats.getDiedAt() != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            text.append("\n**Death**: ").append(dateFormat.format(stats.getDiedAt()));
        }
        if (stats.getYoutubeLink() != null)
            text.append("\n**Youtube**: ").append(stats.getYoutubeLink());
        getDiscordBot().reply(text.toString(), player.getName(), null, ConfigSetting.DISCORDBOT_SHOW_PLAYER_HEADS.getValueAsBoolean() ? "https://minotar.net/helm/" + player.getUUID() + "/32.png".replace("-", "") : null, Color.BLUE, event);
    }
}
