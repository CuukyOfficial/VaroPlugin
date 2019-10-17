package de.cuuky.varo.world.border;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;

public class VaroBorder {

	private World world;
	private Object border;

	public VaroBorder(World world) {
		this.world = world;

		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return;

		try {
			this.border = this.world.getClass().getDeclaredMethod("getWorldBorder").invoke(this.world);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public void setSize(double size, long time) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return;

		try {
			border.getClass().getDeclaredMethod("setSize", double.class, long.class).invoke(border, size, time);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public double getSize() {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return 0;

		try {
			return (double) border.getClass().getDeclaredMethod("getSize").invoke(border);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void setSize(double size) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return;

		try {
			border.getClass().getDeclaredMethod("setSize", double.class).invoke(border, size);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public void setCenter(Location loc) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7)
			return;

		try {
			border.getClass().getDeclaredMethod("setCenter", Location.class).invoke(border, loc);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}

	public void decrease(DecreaseReason reason) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7 || !reason.isEnabled())
			return;

		BorderDecrease decr = new BorderDecrease(reason.getSize(), reason.getDecreaseSpeed());
		decr.setStartHook(new Runnable() {

			@Override
			public void run() {
				reason.postBroadcast();
			}
		});

		decr.setFinishHook(new Runnable() {

			@Override
			public void run() {
				reason.postAlert();
			}
		});
	}

	public double getDistanceTo(Player p) {
		if (VersionUtils.getVersion() == BukkitVersion.ONE_7 || p == null)
			return 0;

		Location ploc = p.getLocation();
		World world = p.getLocation().getWorld();

		Location center;
		double size = 0;
		try {
			Object border = world.getClass().getDeclaredMethod("getWorldBorder").invoke(world);
			center = (Location) border.getClass().getDeclaredMethod("getCenter").invoke(border);
			size = ((double) border.getClass().getDeclaredMethod("getSize").invoke(border)) / 2;
		} catch (Exception e) {
			e.printStackTrace();
			center = p.getLocation();
		}

		center.setY(p.getLocation().getY());
		ArrayList<Double> distanceArray = new ArrayList<>();
		double plocX = ploc.getX() - center.getX();
		double plocZ = ploc.getZ() - center.getZ();
		distanceArray.add(Math.abs(plocX + size));
		distanceArray.add(Math.abs(plocX - size));
		distanceArray.add(Math.abs(plocZ + size));
		distanceArray.add(Math.abs(plocZ - size));
		double nearest = Double.MAX_VALUE;
		for (double distance : distanceArray)
			if (distance < nearest)
				nearest = distance;

		return Math.round(nearest);
	}
}