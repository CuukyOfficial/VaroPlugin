package de.cuuky.varo.alert;

import de.cuuky.varo.serialize.VaroSerializeHandler;
import de.cuuky.varo.serialize.VaroSerializeObject;

public class AlertHandler extends VaroSerializeObject {
	static {
		VaroSerializeHandler.registerEnum(AlertType.class);
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