package de.cuuky.varo.api.event;

public class VaroAPIEvent {

	private boolean cancelAble = true;
	private boolean cancelled = false;

	public VaroAPIEvent() {}

	public VaroAPIEvent(boolean cancelAble) {
		this.cancelAble = cancelAble;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		if(this.cancelAble)
			this.cancelled = cancelled;
	}
}
