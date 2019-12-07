package de.cuuky.varo.alert;

import de.cuuky.varo.serialize.VaroSerializeObject;

public class AlertHandler extends VaroSerializeObject {

	private static AlertHandler instance;

	static {
		VaroSerializeObject.registerEnum(AlertType.class);
	}

	public static void initialise() {
		if (instance == null) {
			instance = new AlertHandler();
		}
	}

	private AlertHandler() {
		super(Alert.class, "/stats/alerts.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for(Alert alert : Alert.getAlerts())
			save(String.valueOf(alert.getId()), alert, getConfiguration());

		saveFile();
	}
}