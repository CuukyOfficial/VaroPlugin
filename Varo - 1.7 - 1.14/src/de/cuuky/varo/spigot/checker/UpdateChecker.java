package de.cuuky.varo.spigot.checker;

import java.net.URL;
import java.util.Scanner;

import de.cuuky.varo.Main;
import de.cuuky.varo.spigot.SpigotObject;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UpdateChecker extends SpigotObject {

	private static UpdateChecker instance;

	private UpdateChecker.UpdateResult result;
	private String version;

	public enum UpdateResult {

		NO_UPDATE("Das Plugin ist auf dem neuesten Stand!"),
		FAIL_SPIGOT("Es konnte keine Verbindung zum Server hergestellt werden."),
		UPDATE_AVAILABLE("Es ist ein Update verfuegbar! Benutze /varo update oder lade es unter https://www.spigotmc.org/resources/" + RESOURCE_ID + "/ herunter"),
		TEST_BUILD("Du nutzt einen TestBuild des Plugins - bitte Fehler umgehend melden!");

		private String message;

		UpdateResult(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	public static UpdateChecker getInstance() {
		if (instance == null) {
			instance = new UpdateChecker();
		}
		return instance;
	}

	private UpdateChecker() {
		this.result = UpdateResult.NO_UPDATE;

		try {
			Scanner scanner = new Scanner(new URL(SPIGET_VARO_RESOURCE_VERSION).openStream());
			String all = "";
			while (scanner.hasNextLine()) {
				all += scanner.nextLine();
			}
			scanner.close();

			JSONObject scannerJSON = (JSONObject) JSONValue.parseWithException(all);
			this.version = scannerJSON.get("name").toString();
			switch (new Version(this.version).compareTo(new Version(Main.getInstance().getDescription().getVersion()))) {
				case EQUAL: result = UpdateResult.NO_UPDATE; break;
				case GREATER: result = UpdateResult.UPDATE_AVAILABLE; break;
				case LOWER: result = UpdateResult.TEST_BUILD; break;
			}
		} catch (Exception e) {
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