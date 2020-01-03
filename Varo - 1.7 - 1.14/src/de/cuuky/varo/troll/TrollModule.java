package de.cuuky.varo.troll;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class TrollModule {

	private static ArrayList<TrollModule> modules;

	static {
		modules = new ArrayList<>();
	}

	protected String description;
	protected ArrayList<Player> enabledFor;
	protected Material icon;
	protected String name;

	public TrollModule(String name, Material icon, String description) {
		this.name = name;
		this.icon = icon;
		this.description = description;
		this.enabledFor = new ArrayList<>();

		modules.add(this);
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Player> getEnabledFor() {
		return enabledFor;
	}

	public Material getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public void setEnabledFor(Player player, boolean enable) {
		if(enable) {
			enabledFor.add(player);
			onEnable(player);
		} else {
			enabledFor.remove(player);
			onDisable(player);
		}
	}

	protected void onDisable(Player player) {}

	protected void onEnable(Player player) {}

	protected void onInteract(PlayerInteractEvent event) {}

	protected void onMove(PlayerMoveEvent event) {}

	public static ArrayList<TrollModule> getEnabledModules(Player player) {
		ArrayList<TrollModule> enabledEvents = new ArrayList<>();
		for(TrollModule module : modules)
			if(module.getEnabledFor().contains(player))
				enabledEvents.add(module);

		return enabledEvents;
	}

	public static ArrayList<TrollModule> getModules() {
		return modules;
	}
}