package de.cuuky.varo.report;

import de.cuuky.varo.serialize.VaroSerializeObject;

public class ReportHandler extends VaroSerializeObject {

	private static ReportHandler instance;

	static {
		registerEnum(ReportReason.class);
	}

	public static void initialise() {
		if(instance == null) {
			instance = new ReportHandler();
		}
	}

	private ReportHandler() {
		super(Report.class, "/stats/reports.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for(Report report : Report.getReports())
			save(String.valueOf(report.getId()), report, getConfiguration());

		saveFile();
	}
}