package de.cuuky.varo.world.border;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;
import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.world.border.decrease.BorderDecrease;
import de.cuuky.varo.world.border.decrease.DecreaseReason;

public class VaroBorder {

	private static VaroBorder instance;

	private Object borders[];
	private HashMap<Player, Double> distances;

	private VaroBorder() {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		this.borders = new Object[2];
		this.distances = new HashMap<>();

		loadBorders();
		startCalculating();
	}

	private void loadBorders() {
		for(World world : Bukkit.getWorlds()) {
			switch(world.getEnvironment()) {
			case NORMAL:
				try {
					// Invoke, damit kein Fehler in einer IDE kommt mit 1.7 jar
					borders[0] = world.getClass().getDeclaredMethod("getWorldBorder").invoke(world);
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case NETHER:
				try {
					borders[1] = world.getClass().getDeclaredMethod("getWorldBorder").invoke(world);
				} catch(Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	}

	private double getDistanceToBorder(Player player) {
		Location playerLoc = player.getLocation();
		World playerWorld = player.getLocation().getWorld();

		Location center;
		double size = 0;

		Object border;

		switch(playerWorld.getEnvironment()) {
		case NORMAL:
			border = borders[0];
			break;
		case NETHER:
			border = borders[1];
			break;
		default:
			return -1;
		}

		try {
			center = (Location) border.getClass().getDeclaredMethod("getCenter").invoke(border);
			size = ((double) border.getClass().getDeclaredMethod("getSize").invoke(border)) / 2;
		} catch(Exception e) {
			e.printStackTrace();
			return -1;
		}

		ArrayList<Double> distanceArray = new ArrayList<>();
		double playerDifferenceX = playerLoc.getX() - center.getX();
		double playerDifferenceZ = playerLoc.getZ() - center.getZ();
		distanceArray.add(Math.abs(playerDifferenceX + size));
		distanceArray.add(Math.abs(playerDifferenceX - size));
		distanceArray.add(Math.abs(playerDifferenceZ + size));
		distanceArray.add(Math.abs(playerDifferenceZ - size));
		double nearest = Double.MAX_VALUE;
		for(double distance : distanceArray)
			if(distance < nearest)
				nearest = distance;

		return Math.round(nearest);
	}

	private void startCalculating() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				for(Player player : VersionUtils.getOnlinePlayer())
					distances.put(player, getDistanceToBorder(player));
			}
		}, 20, 20 * 1);
	}

	public void decreaseBorder(DecreaseReason reason) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7) || !reason.isEnabled())
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

	public double getBorderDistanceTo(Player p) {
		if(p == null || distances == null || !distances.containsKey(p))
			return 0;

		return distances.get(p);
	}

	public double getBorderSize(World world) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return 0;

		Object border;

		if(world == null) {
			border = borders[0];
		} else {
			switch(world.getEnvironment()) {
			case NORMAL:
				border = borders[0];
				break;
			case NETHER:
				border = borders[1];
				break;
			default:
				return 0;
			}
		}

		try {
			return (double) border.getClass().getDeclaredMethod("getSize").invoke(border);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void setBorderCenter(Location loc) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		Object border;

		switch(loc.getWorld().getEnvironment()) {
		case NORMAL:
			border = borders[0];
			break;
		case NETHER:
			border = borders[1];
			break;
		default:
			return;
		}

		try {
			border.getClass().getDeclaredMethod("setCenter", Location.class).invoke(border, loc);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setBorderSize(double size, long time, World world) {
		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_7))
			return;

		Object border;

		if(world == null) {
			border = borders[0];
		} else {
			switch(world.getEnvironment()) {
			case NORMAL:
				border = borders[0];
				break;
			case NETHER:
				border = borders[1];
				break;
			default:
				return;
			}
		}

		try {
			border.getClass().getDeclaredMethod("setSize", double.class, long.class).invoke(border, size, time);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static VaroBorder getInstance() {
		if(instance == null) 
			instance = new VaroBorder();
		
		return instance;
	}
}