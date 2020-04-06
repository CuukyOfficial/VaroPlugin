package de.cuuky.varo.utils;

import java.util.HashSet;

import de.koburs.APIMain;
import org.bukkit.command.CommandSender;

public class PlayerCheckUtils {

	private PlayerCheckUtils() {}

	private static HashSet<String> playersSure = new HashSet<>();

	public static boolean checkPlayer(CommandSender sender, String playerName) {
		APIMain.PlayerStates state = APIMain.testStatus(playerName);
		switch (state) {
			case KICK_LIST_3: sender.sendMessage("§4Dieser Spieler ist auf der Koburs Liste 3 und kann daher nicht hinzugefügt werden."); return false;
			case KICK_LIST_2: sender.sendMessage("§4Dieser Spieler ist auf der Koburs Liste 2 und darf nach deinen Einstellungen nicht hinzugefügt werden."); return false;
			case KICK_LIST_1: case KICK_LIST_1_SPECIFIC: sender.sendMessage("§4Dieser Spieler ist auf der Koburs Liste 1 und darf nach deinen Einstellungen nicht hinzugefügt werden."); return false;
			case WARN_LIST_1: case NO_ADMIN_AND_WARN:
				if (testPlayerSure(playerName)) {
					return true;
				} else {
					sender.sendMessage("§4Dieser Spieler ist auf der Koburs Liste 1 wegen " + APIMain.getListReason(playerName, 1) + "\n" +
							"Es wird stark empfohlen, ihn nicht zu deinem Projekt hinzuzufügen.\n" +
							"Gebe den Befehl noch einmal ein, wenn du dir sicher bist, ihn doch hinzuzufügen zu wollen"); return false;
				}
			default: return true;
		}
	}

	private static boolean testPlayerSure(String playerName) {
		if (playersSure.contains(playerName)) {
			playersSure.remove(playerName);
			return true;
		} else {
			playersSure.add(playerName);
			return false;
		}
	}

}