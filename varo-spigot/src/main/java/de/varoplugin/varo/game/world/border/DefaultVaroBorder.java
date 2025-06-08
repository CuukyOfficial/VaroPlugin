package de.varoplugin.varo.game.world.border;

import org.bukkit.Location;
import org.bukkit.World;

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
        this.world.getWorldBorder().setSize(size, time);
    }
    
    @Override
    public Location getCenter() {
        return this.world.getWorldBorder().getCenter();
    }

    @Override
    public void setCenter(Location location) {
        this.world.getWorldBorder().setCenter(location);
    }
}