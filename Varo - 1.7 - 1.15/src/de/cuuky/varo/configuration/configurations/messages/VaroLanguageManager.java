package de.cuuky.varo.configuration.configurations.messages;

import java.util.ArrayList;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.messages.language.LanguageManager;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageDE;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageEN;
import de.cuuky.varo.configuration.configurations.messages.language.languages.LanguageMessage;
import de.cuuky.varo.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;

public class VaroLanguageManager extends LanguageManager {
	
	public VaroLanguageManager() {
		super(Main.getInstance());
		
		registerLanguage("en_EN", LanguageEN.class, true);
		registerLanguage("de_DE", LanguageDE.class, false);
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

	public String getValue(LanguageMessage message, VaroPlayer vplayer) {
		String replaced = super.getMessage(message.getMessageID(), vplayer != null ? vplayer.getNetworkManager().getLocale() : null);

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

		replaced = GeneralMessagePlaceholder.replacePlaceholders(replaced);
		return vplayer != null ? PlayerMessagePlaceholder.replacePlaceholders(replaced, vplayer) : replaced;
	}

	public String getValue(LanguageMessage message) {
		return this.getValue(message, null);
	}
}