package de.varoplugin.varo.game.world.border;

import org.bukkit.Location;
import org.bukkit.World;

import de.varoplugin.cfw.scheduler.TaskScheduler;
import de.varoplugin.varo.Main;

public class DefaultVaroBorder implements RectangularVaroBorder {

	private final World world;

	public DefaultVaroBorder(World world) {
		this.world = world;
	}

    @Override
    public double getSize() {
        return this.world.getWorldBorder().getSize();
    }

    @Override
    public void setSize(double size, long time) {
        TaskScheduler.of(Main.getInstance()).execute(() -> this.world.getWorldBorder().setSize(size, time));
    }
    
    @Override
    public Location getCenter() {
        return this.world.getWorldBorder().getCenter();
    }

    @Override
    public void setCenter(Location location) {
        TaskScheduler.of(Main.getInstance()).execute(() -> this.world.getWorldBorder().setCenter(location));
    }
}