package de.varoplugin.varo.logger.logger;

import com.google.gson.annotations.Expose;

import de.varoplugin.varo.logger.PlayerLogElement;

public class LoggedBlock extends PlayerLogElement {

	@Expose
	private String material;
	@Expose
	private String world;
	@Expose
	private int x;
	@Expose
	private int y;
	@Expose
	private int z;

	public LoggedBlock(long timestamp, String uuid, String name, String material, String world, int x, int y, int z) {
		super(timestamp, name, uuid);
		this.material = material;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getMaterial() {
		return material;
	}

	public String getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
}
