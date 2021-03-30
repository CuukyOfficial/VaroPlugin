package de.cuuky.varo.api.event;

public class VaroAPIEvent {

	private boolean cancelAble;
	private boolean cancelled;

	public VaroAPIEvent() {
		this.cancelAble = true;
	}

	public VaroAPIEvent(boolean cancelAble) {
		this.cancelAble = cancelAble;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		if (this.cancelAble)
			this.cancelled = cancelled;
	}
}
