package de.cuuky.varo.api.event;

public class VaroAPIEvent {

	private boolean cancelled = false;
	private boolean cancelAble = true;

	public VaroAPIEvent(boolean cancelAble) {
		this.cancelAble = cancelAble;
	}

	public VaroAPIEvent() {
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		if (this.cancelAble)
			this.cancelled = cancelled;
	}
}
