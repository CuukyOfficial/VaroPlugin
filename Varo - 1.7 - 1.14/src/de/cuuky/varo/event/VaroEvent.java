package de.cuuky.varo.event;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import de.cuuky.varo.event.events.ExposedVaroEvent;
import de.cuuky.varo.event.events.MoonGravityVaroEvent;
import de.cuuky.varo.event.events.PoisonRainVaroEvent;
import de.cuuky.varo.event.events.PoisonWaterVaroEvent;
import de.cuuky.varo.event.events.MassRecordingVaroEvent;

public class VaroEvent {

	private static ArrayList<VaroEvent> events;
	private static MassRecordingVaroEvent MassRecEvent;

	static {
		events = new ArrayList<>();
		
		MassRecEvent = new MassRecordingVaroEvent();
		new PoisonWaterVaroEvent();
		new PoisonRainVaroEvent();
		new MoonGravityVaroEvent();
		new ExposedVaroEvent();
	}

	private String name;
	private Material icon;
	private String description;
	protected boolean enabled;

	public VaroEvent(String name, Material icon, String description) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.enabled = false;

		events.add(this);
	}

	public String getName() {
		return name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;

		if(enabled)
			onEnable();
		else
			onDisable();
	}

	public Material getIcon() {
		return icon;
	}

	public String getDescription() {
		return description;
	}

	public void onEnable() {}

	public void onDisable() {}

	public void onInteract(PlayerInteractEvent event) {}

	public void onMove(PlayerMoveEvent event) {}

	public static ArrayList<VaroEvent> getEvents() {
		return events;
	}
	
	public static MassRecordingVaroEvent getMassRecEvent() {
		return MassRecEvent;
	}

	public static ArrayList<VaroEvent> getEnabledEvents() {
		ArrayList<VaroEvent> enabledEvents = new ArrayList<>();
		for(VaroEvent event : events)
			if(event.isEnabled())
				enabledEvents.add(event);

		return enabledEvents;
	}
}
