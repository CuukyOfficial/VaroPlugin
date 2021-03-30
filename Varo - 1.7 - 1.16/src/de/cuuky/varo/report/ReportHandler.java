package de.cuuky.varo.report;

import de.cuuky.varo.serialize.VaroSerializeObject;

public class ReportHandler extends VaroSerializeObject {

	static {
		registerEnum(ReportReason.class);
	}

	public ReportHandler() {
		super(Report.class, "/stats/reports.yml");

		load();
	}

	@Override
	public void onSave() {
		clearOld();

		for (Report report : Report.getReports())
			save(String.valueOf(report.getId()), report, getConfiguration());

		saveFile();
	}
}