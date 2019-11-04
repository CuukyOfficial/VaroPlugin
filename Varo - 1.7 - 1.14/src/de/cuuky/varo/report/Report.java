package de.cuuky.varo.report;

import java.util.ArrayList;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;
import de.cuuky.varo.utils.Utils;

public class Report implements VaroSerializeable {

	private static ArrayList<Report> reports = new ArrayList<>();

	@VaroSerializeField(path = "open")
	private boolean open;
	@VaroSerializeField(path = "id")
	private int id;
	@VaroSerializeField(path = "reason")
	private ReportReason reason;
	@VaroSerializeField(path = "reporterId")
	private int reporterId;
	@VaroSerializeField(path = "reportedId")
	private int reportedId;

	private VaroPlayer reporter;
	private VaroPlayer reported;

	public Report() {
		reports.add(this);
	}

	public Report(VaroPlayer reporter, VaroPlayer reported, ReportReason reason) {
		this.reported = reported;
		this.reporter = reporter;
		this.reason = reason;
		this.open = true;
		this.id = generateId();

		reports.add(this);
	}

	public boolean isOpen() {
		return open;
	}

	public void close() {
		this.open = false;
		reports.remove(this);
	}

	private int generateId() {
		int id = Utils.randomInt(1000, 9999999);
		while(getReport(id) != null)
			generateId();

		return id;
	}

	public ReportReason getReason() {
		return reason;
	}

	public int getId() {
		return id;
	}

	public VaroPlayer getReported() {
		return reported;
	}

	public static ArrayList<Report> getReports() {
		return reports;
	}

	public VaroPlayer getReporter() {
		return reporter;
	}

	public static Report getReport(int id) {
		for(Report r : reports)
			if(r.getId() == id)
				return r;

		return null;
	}

	@Override
	public void onDeserializeEnd() {
		this.reported = VaroPlayer.getPlayer(reportedId);
		this.reporter = VaroPlayer.getPlayer(reporterId);
	}

	@Override
	public void onSerializeStart() {
		if(reporter != null)
			this.reporterId = reporter.getId();
		if(reported != null)
			this.reportedId = reported.getId();
	}
}
