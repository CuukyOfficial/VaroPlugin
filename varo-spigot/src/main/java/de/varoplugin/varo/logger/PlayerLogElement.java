package de.varoplugin.varo.logger;

import com.google.gson.annotations.Expose;

public class PlayerLogElement extends LogElement {

	@Expose
	private String uuid;
	@Expose
	private String name;

	public PlayerLogElement(long timestamp, String name, String uuid) {
		super(timestamp);
		this.uuid = uuid;
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}
}
