package de.cuuky.varo.configuration.configurations.language;

import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.cuuky.varo.Main;
import de.cuuky.varo.player.VaroPlayer;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIAdapter {

    public String setGeneralPlaceholders(String text) {
        return PlaceholderAPI.setPlaceholders(null, text);
    }

    public String setPlayerPlaceholders(String text, VaroPlayer vp) {
        return vp.isOnline() ?
                PlaceholderAPI.setPlaceholders(vp.getPlayer(), text) : text;
    }
    
    public static class VaroPlaceholderExpansion extends PlaceholderExpansion {

        @Override
        public @NotNull String getIdentifier() {
            return Main.getInstance().getName();
        }

        @Override
        public @NotNull String getAuthor() {
            return Main.getInstance().getDescription().getAuthors().stream().collect(Collectors.joining(", "));
        }

        @Override
        public @NotNull String getVersion() {
            return Main.getInstance().getDescription().getVersion();
        }
        
        @Override
        public boolean persist() {
            return true;
        }
        
        @Override
        public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
            String placeholder = "%" + params + "%";
            if (player != null) {
                VaroPlayer varoPlayer = VaroPlayer.getPlayer(player);
                if (varoPlayer != null)
                    return Main.getLanguageManager().replaceMessage(placeholder, varoPlayer, false);
            }
            return Main.getLanguageManager().replaceMessage(placeholder, false);
        }
    }
}