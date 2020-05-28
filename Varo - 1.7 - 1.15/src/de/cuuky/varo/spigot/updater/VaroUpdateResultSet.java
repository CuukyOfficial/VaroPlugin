package de.cuuky.varo.spigot.updater;

public class VaroUpdateResultSet {

	public enum UpdateResult {

		FAIL_SPIGOT("Failed to connect to the update servers."),
		NO_UPDATE("The plugin is up to date!"),
		TEST_BUILD("The plugin is up to date! (Test-Build)"),
		UPDATE_AVAILABLE("A new update is available! Use /varo update to update or download it manually at https://www.spigotmc.org/resources/71075/");

		private final String message;

		UpdateResult(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	private final UpdateResult updateResult;
	private final String versionName;
	private final String versionId;

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