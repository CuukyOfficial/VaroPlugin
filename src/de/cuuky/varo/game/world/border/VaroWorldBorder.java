package de.cuuky.varo.game.world.border;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.cfw.version.ServerVersion;
import de.varoplugin.cfw.version.VersionUtils;
import de.cuuky.varo.Main;

public class VaroWorldBorder {

	private static Method setCenterMethod, getCenterMethod, setSizeMethod, getSizeMethod;

	static {
		if (VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7)) {
			try {
				Class<?> borderClass = Class.forName("org.bukkit.WorldBorder");

				setCenterMethod = borderClass.getDeclaredMethod("setCenter", Location.class);
				getCenterMethod = borderClass.getDeclaredMethod("getCenter");
				setSizeMethod = borderClass.getDeclaredMethod("setSize", double.class, long.class);
				getSizeMethod = borderClass.getDeclaredMethod("getSize");
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}

	private World world;
	private Object border;
	private HashMap<Player, Double> distances;

	public VaroWorldBorder(World world) {
		if (!VersionUtils.getVersion().isHigherThan(ServerVersion.ONE_7))
			return;

		this.world = world;
		this.distances = new HashMap<>();

		loadBorder();
		startCalculating();
	}

	private void loadBorder() {
		try {
			border = world.getClass().getDeclaredMethod("getWorldBorder").invoke(world);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	private double getDistanceToBorder(Player player) {
		Location playerLoc = player.getLocation();
		Location center = getCenter();
		double size = getBorderSize() / 2;
		double dx = Math.abs(playerLoc.getX() - center.getX()) - size;
		double dz = Math.abs(playerLoc.getZ() - center.getZ()) - size;
		return Math.abs(Math.max(dx, dz));
	}

	private void startCalculating() {
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : VersionUtils.getVersionAdapter().getOnlinePlayers())
					distances.put(player, getDistanceToBorder(player));
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 20, 20);
	}

	public Location getCenter() {
		try {
			return (Location) getCenterMethod.invoke(border);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public double getBorderDistanceTo(Player p) {
		if (p == null || distances == null || !distances.containsKey(p))
			return 0;

		return distances.get(p);
	}

	public double getBorderSize() {
		try {
			return (double) getSizeMethod.invoke(border);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void setBorderCenter(Location loc) {
		try {
			setCenterMethod.invoke(border, loc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBorderSize(double size, long time) {
		new BukkitRunnable() {
			@Override
			public void run() {
				try {
					setSizeMethod.invoke(border, size, time);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.runTask(Main.getInstance());
	}
}