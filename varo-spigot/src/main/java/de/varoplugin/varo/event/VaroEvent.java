package de.varoplugin.varo.event;

import java.util.ArrayList;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.cryptomorin.xseries.XMaterial;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.event.events.ExposedVaroEvent;
import de.varoplugin.varo.event.events.MassRecordingVaroEvent;
import de.varoplugin.varo.event.events.MoonGravityVaroEvent;
import de.varoplugin.varo.event.events.PoisonRainVaroEvent;
import de.varoplugin.varo.event.events.PoisonWaterVaroEvent;

public abstract class VaroEvent {

	private static ArrayList<VaroEvent> events;

	static {
		events = new ArrayList<>();

		new MassRecordingVaroEvent();
		new PoisonWaterVaroEvent();
		new PoisonRainVaroEvent();
		new MoonGravityVaroEvent();
		new ExposedVaroEvent();
	}

	private VaroEventType eventType;
	private String description;
	private XMaterial icon;
	protected boolean enabled;

	public VaroEvent(VaroEventType eventType, XMaterial icon, String description) {
		this.eventType = eventType;
		this.icon = icon;
		this.description = description;
		this.enabled = false;

		events.add(this);
	}

	public String getDescription() {
		return description;
	}

	public XMaterial getIcon() {
		return icon;
	}

	public VaroEventType getEventType() {
		return this.eventType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if ((!Main.getVaroGame().isRunning() && enabled) || enabled == this.enabled)
			return;

		if (enabled)
			onEnable();
		else
			onDisable();

		this.enabled = enabled;
	}

	public void onDisable() {}

	public void onEnable() {}

	public void onInteract(PlayerInteractEvent event) {}

	public void onMove(PlayerMoveEvent event) {}

	public static ArrayList<VaroEvent> getEnabledEvents() {
		ArrayList<VaroEvent> enabledEvents = new ArrayList<>();
		for (VaroEvent event : events)
			if (event.isEnabled())
				enabledEvents.add(event);

		return enabledEvents;
	}

	public static VaroEvent getEvent(VaroEventType eventType) {
		for (VaroEvent event : events)
			if (event.getEventType() == eventType)
				return event;

		throw new IllegalArgumentException("Unknown event type: " + eventType);
	}

	public static ArrayList<VaroEvent> getEvents() {
		return events;
	}
}