package de.cuuky.varo;

import de.cuuky.cfw.configuration.serialization.Serialize;
import de.cuuky.varo.entity.player.VaroPlayer;

// TODO: Remove or make ReportReason dynamic and remove getters
public class Report extends VaroElement {

	@Serialize("open")
	private boolean open;

	@Serialize("reason")
	private ReportReason reason;

	@Serialize("reportedId")
	private int reportedId;

	@Serialize("reporterId")
	private int reporterId;

	private VaroPlayer reporter, reported;

	public Report(VaroPlayer reporter, VaroPlayer reported, ReportReason reason) {
		this.reported = reported;
		this.reporter = reporter;
        this.reportedId = reported.getId();
        this.reporterId = reporter.getId();
		this.reason = reason;
		this.open = true;
	}

    @Override
    protected void registerPolicies() {
        this.registerPolicy(ReportReason.class, this.reason::getName, ReportReason::getByName);
        super.registerPolicies();
    }

    @Override
    protected void onInitialize(Varo varo) {
        this.reported = varo.getPlayer(reportedId);
        this.reporter = varo.getPlayer(reporterId);
    }

    @Override
    public void remove() {
        this.open = false;
        super.remove();
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
}