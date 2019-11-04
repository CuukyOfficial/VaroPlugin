package de.cuuky.varo.spigot.checker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.plugin.java.JavaPlugin;

import de.cuuky.varo.Main;
import de.cuuky.varo.spigot.SpigotObject;

public class UpdateChecker extends SpigotObject {

	private UpdateChecker.UpdateResult result;
	private String version;

	public enum UpdateResult {

		NO_UPDATE("Es ist kein Update verfügbar!"),
		FAIL_SPIGOT("Es konnte keine Verbindung zum Server hergestellt werden."),
		UPDATE_AVAILABLE("Es ist ein Update verfuegbar! https://www.spigotmc.org/resources/" + RESOURCE_ID + "/"),
		TEST_BUILD("Du nutzt einen TestBuild des Plugins - bitte Fehler umgehend melden!");

		private String message;

		private UpdateResult(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	public UpdateChecker(JavaPlugin plugin) {
		this.result = UpdateResult.NO_UPDATE;

		try {
			HttpURLConnection con = (HttpURLConnection) new URL(SPIGET_VARO_RESOURCE_VERSION).openConnection();
			con.setConnectTimeout(2000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while((line = reader.readLine()) != null) {
				if(!line.contains("name"))
					continue;
				
				this.version = line.split(": ")[1].split(",")[0].replaceAll("\"", "");
			}
			
			switch(new Version(this.version).compareTo(new Version(Main.getInstance().getDescription().getVersion()))) {
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
