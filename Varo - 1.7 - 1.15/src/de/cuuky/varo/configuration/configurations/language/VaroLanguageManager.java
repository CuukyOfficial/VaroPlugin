package de.cuuky.varo.configuration.configurations.language;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.cfw.configuration.language.Language;
import de.cuuky.cfw.configuration.language.LanguageManager;
import de.cuuky.cfw.configuration.placeholder.placeholder.type.MessagePlaceholderType;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.configuration.configurations.language.languages.LanguageEN;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class VaroLanguageManager extends LanguageManager {

	private static final String PATH_DIR = "plugins/Varo/languages", FALLBACK_LANGUAGE = "de_de";

	public VaroLanguageManager(JavaPlugin ownerInstance) {
		super(PATH_DIR, FALLBACK_LANGUAGE, ownerInstance);

		loadLanguages();
	}

	@Override
	public void loadLanguages() {
		registerLoadableLanguage("de_de", ConfigMessages.class);
		registerLoadableLanguage("en_us", LanguageEN.class);

		super.loadLanguages();

		Language lang = getLanguages().get(ConfigSetting.MAIN_LANGUAGE.getValueAsString());
		if (lang == null || lang.getClazz() == null)
			throw new NullPointerException("Couldn't find language '" + ConfigSetting.MAIN_LANGUAGE.getValueAsString() + "'");

		setDefaultLanguage(lang);
	}

	private ArrayList<Integer> getConvNumbers(String line, String key) {
		ArrayList<Integer> list = new ArrayList<>();

		boolean first = true;
		for (String split0 : line.split(key)) {
			if (first) {
				first = false;
				if (!line.startsWith(key))
					continue;
			}

			String[] split1 = split0.split("%", 2);

			if (split1.length == 2) {
				try {
					list.add(Integer.parseInt(split1[0]));
				} catch (NumberFormatException e) {
					continue;
				}
			}
		}

		return list;
	}

	public String replaceMessage(String message) {
		String replaced = message;

		for (int rank : getConvNumbers(replaced, "%topplayer-")) {
			VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
			replaced = replaced.replace("%topplayer-" + rank + "%", (player == null ? "-" : player.getName()));
		}

		for (int rank : getConvNumbers(replaced, "%topplayerkills-")) {
			VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
			replaced = replaced.replace("%topplayerkills-" + rank + "%", (player == null ? "0" : String.valueOf(player.getStats().getKills())));
		}

		for (int rank : getConvNumbers(replaced, "%topteam-")) {
			VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
			replaced = replaced.replace("%topteam-" + rank + "%", (team == null ? "-" : team.getName()));
		}

		for (int rank : getConvNumbers(replaced, "%topteamkills-")) {
			VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
			replaced = replaced.replace("%topteamkills-" + rank + "%", (team == null ? "0" : String.valueOf(team.getKills())));
		}

		return Main.getCuukyFrameWork().getPlaceholderManager().replacePlaceholders(replaced, null, MessagePlaceholderType.GENERAL);
	}

	public String replaceMessage(String message, CustomPlayer player) {
		return Main.getCuukyFrameWork().getPlaceholderManager().replacePlaceholders(replaceMessage(message), (VaroPlayer) player, MessagePlaceholderType.PLAYER);
	}
}