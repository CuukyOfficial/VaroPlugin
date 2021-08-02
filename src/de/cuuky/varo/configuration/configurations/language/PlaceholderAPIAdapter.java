package de.cuuky.varo.configuration.configurations.language;

import de.cuuky.varo.entity.player.VaroPlayer;
import me.clip.placeholderapi.PlaceholderAPI;

public class PlaceholderAPIAdapter {

    public String setGeneralPlaceholders(String text) {
        return PlaceholderAPI.setPlaceholders(null, text);
    }

    public String setPlayerPlaceholders(String text, VaroPlayer vp) {
        return vp.isOnline() ?
                PlaceholderAPI.setPlaceholders(vp.getPlayer(), text) : text;
    }
}