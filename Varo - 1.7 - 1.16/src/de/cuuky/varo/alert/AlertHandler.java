package de.cuuky.varo.alert;

import de.cuuky.varo.serialize.VaroSerializeObject;

public class AlertHandler extends VaroSerializeObject {
	static {
		VaroSerializeObject.registerEnum(AlertType.class);
	}

	public AlertHandler() {
		super(Alert.class, "/stats/alerts.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for (Alert alert : Alert.getAlerts())
			save(String.valueOf(alert.getId()), alert, getConfiguration());

		saveFile();
	}
}