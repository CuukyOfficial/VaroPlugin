package de.varoplugin.varo.report;

import java.util.ArrayList;

import de.varoplugin.cfw.utils.JavaUtils;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;

public class Report implements VaroSerializeable {

	private static ArrayList<Report> reports;

	static {
		reports = new ArrayList<>();
	}

	@VaroSerializeField(path = "id")
	private int id;

	@VaroSerializeField(path = "open")
	private boolean open;

	@VaroSerializeField(path = "reason")
	private ReportReason reason;

	@VaroSerializeField(path = "reportedId")
	private int reportedId;

	@VaroSerializeField(path = "reporterId")
	private int reporterId;

	private VaroPlayer reporter, reported;

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

	private int generateId() {
		int id;

		do {
			id = JavaUtils.randomInt(1000, 9999999);
		} while (getReport(id) != null);

		return id;
	}

	public void close() {
		this.open = false;
		reports.remove(this);
	}

	public int getId() {
		return id;
	}

	public ReportReason getReason() {
		return reason;
	}

	public VaroPlayer getReported() {
		return reported;
	}

	public VaroPlayer getReporter() {
		return reporter;
	}

	public boolean isOpen() {
		return open;
	}

	@Override
	public void onDeserializeEnd() {
		this.reported = VaroPlayer.getPlayer(reportedId);
		this.reporter = VaroPlayer.getPlayer(reporterId);
	}

	@Override
	public void onSerializeStart() {
		if (reporter != null)
			this.reporterId = reporter.getId();
		if (reported != null)
			this.reportedId = reported.getId();
	}

	public static Report getReport(int id) {
		for (Report r : reports)
			if (r.getId() == id)
				return r;

		return null;
	}

	public static ArrayList<Report> getReports() {
		return reports;
	}
}