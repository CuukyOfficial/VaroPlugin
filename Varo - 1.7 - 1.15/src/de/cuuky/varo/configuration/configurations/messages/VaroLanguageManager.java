package de.cuuky.varo.configuration.configurations.messages;

import java.util.ArrayList;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.messages.language.Language;
import de.cuuky.varo.configuration.configurations.messages.language.LanguageManager;
import de.cuuky.varo.configuration.configurations.messages.language.languages.DefaultLanguage;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageEN;
import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class VaroLanguageManager extends LanguageManager {

	private static final String PATH_DIR = "plugins/Varo/languages";

	public VaroLanguageManager() {
		super(PATH_DIR);

		registerDefaultLanguage("en_US", LanguageEN.class);
		registerDefaultLanguage("de_DE", LanguageDE.class);

		loadLanguages();
		
		Language lang = getLanguages().get(ConfigSetting.MAIN_LANGUAGE.getValueAsString());
		if(lang == null)
			throw new NullPointerException("Couldn't find language '" + ConfigSetting.MAIN_LANGUAGE.getValueAsString() + "'");
		
		setDefaultLanguage(lang);
	}
	
	public String replaceMessage(String message) {
		String replaced = message; 
		
		for(int rank : getConvNumbers(replaced, "%topplayer-")) {
			VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
			replaced = replaced.replace("%topplayer-" + rank + "%", (player == null ? "-" : player.getName()));
		}

		for(int rank : getConvNumbers(replaced, "%topplayerkills-")) {
			VaroPlayer player = Main.getVaroGame().getTopScores().getPlayer(rank);
			replaced = replaced.replace("%topplayerkills-" + rank + "%", (player == null ? "0" : String.valueOf(player.getStats().getKills())));
		}

		for(int rank : getConvNumbers(replaced, "%topteam-")) {
			VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
			replaced = replaced.replace("%topteam-" + rank + "%", (team == null ? "-" : team.getName()));
		}

		for(int rank : getConvNumbers(replaced, "%topteamkills-")) {
			VaroTeam team = Main.getVaroGame().getTopScores().getTeam(rank);
			replaced = replaced.replace("%topteamkills-" + rank + "%", (team == null ? "0" : String.valueOf(team.getKills())));
		}

		return GeneralMessagePlaceholder.replacePlaceholders(replaced);
	}
	
	public String replaceMessage(String message, VaroPlayer player) {
		return PlayerMessagePlaceholder.replacePlaceholders(replaceMessage(message), player);
	}

	private ArrayList<Integer> getConvNumbers(String line, String key) {
		ArrayList<Integer> list = new ArrayList<>();

		boolean first = true;
		for(String split0 : line.split(key)) {
			if(first) {
				first = false;
				if(!line.startsWith(key))
					continue;
			}

			String[] split1 = split0.split("%", 2);

			if(split1.length == 2) {
				try {
					list.add(Integer.parseInt(split1[0]));
				} catch(NumberFormatException e) {
					continue;
				}
			}
		}

		return list;
	}

	public String getValue(DefaultLanguage message, VaroPlayer languageHolder, VaroPlayer vplayer) {
		String replaced = super.getMessage(message.getPath(), languageHolder != null && languageHolder.getNetworkManager() != null ? languageHolder.getNetworkManager().getLocale() : null);
		return vplayer != null ? replaceMessage(replaced, vplayer) : replaceMessage(replaced);
	}

	public String getValue(DefaultLanguage message, VaroPlayer languageHolder) {
		return this.getValue(message, languageHolder, null);
	}
	
	public String getValue(DefaultLanguage message) {
		return this.getValue(message, null, null);
	}
}