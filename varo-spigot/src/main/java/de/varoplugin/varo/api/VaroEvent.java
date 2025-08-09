package de.varoplugin.varo.api;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class VaroEvent extends Event implements Cancellable {

	private boolean cancelled;

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}
}
