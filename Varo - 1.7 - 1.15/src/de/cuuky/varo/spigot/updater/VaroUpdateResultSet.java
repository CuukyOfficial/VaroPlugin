package de.cuuky.varo.spigot.updater;

public class VaroUpdateResultSet {
	
	public static enum UpdateResult {

		FAIL_SPIGOT("Es konnte keine Verbindung zum Server hergestellt werden."),
		NO_UPDATE("Das Plugin ist auf dem neuesten Stand!"),
		TEST_BUILD("Das Plugin ist auf dem neuesten Stand! (Test-Build)"),
		UPDATE_AVAILABLE("Es ist ein Update verfuegbar! Benutze /varo update oder lade es manuell unter https://www.spigotmc.org/resources/71075/ herunter");

		private String message;

		private UpdateResult(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
	
	private UpdateResult updateResult;
	private String versionName, versionId;
	
	public VaroUpdateResultSet(UpdateResult result, String versionName, String versionId) {
		this.updateResult = result;
		this.versionName = versionName;
		this.versionId = versionId;
	}
	
	public UpdateResult getUpdateResult() {
		return this.updateResult;
	}
	
	public String getVersionName() {
		return this.versionName;
	}
	
	public String getVersionId() {
		return this.versionId;
	}
}