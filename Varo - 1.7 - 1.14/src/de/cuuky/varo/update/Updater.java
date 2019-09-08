package de.cuuky.varo.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.Main;

public class Updater {

	private Updater.UpdateResult result;
	private String version;
	private Integer rescourceId;

	public enum UpdateResult {

		NO_UPDATE("Es ist kein Update verfuegbar! https://www.spigotmc.org/resources/71075/"),
		FAIL_SPIGOT("Es konnte keine Verbindung zum Server hergestellt werden."),
		UPDATE_AVAILABLE("Es ist ein Update verfuegbar!"),
		TEST_BUILD("Du nutzt einen TestBuild des Plugins - bitte Fehler umgehend melden!");

		private String message;

		private UpdateResult(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	public Updater(JavaPlugin plugin, Integer resourceId) {
		this.result = UpdateResult.NO_UPDATE;
		this.rescourceId = resourceId;
		if(resourceId == 0)
			return;

		try {
			HttpURLConnection con = (HttpURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId).openConnection();
			version = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
			switch(new Version(version).compareTo(new Version(Main.getInstance().getDescription().getVersion()))) {
			case EQUAL:
				result = UpdateResult.NO_UPDATE;
				break;
			case GREATER:
				result = UpdateResult.UPDATE_AVAILABLE;
				break;
			case LOWER:
				result = UpdateResult.TEST_BUILD;
				break;
			}
		} catch(Exception ex) {
			result = UpdateResult.FAIL_SPIGOT;
		}
	}

	public void postResults() {
		if(rescourceId == 0)
			return;

		System.out.println(Main.getConsolePrefix() + "Updater: " + result.getMessage());
	}

	public String getResultingMessage() {
		return result.getMessage();
	}

	public UpdateResult getResult() {
		return result;
	}

	public String getVersion() {
		return this.version;
	}
}
