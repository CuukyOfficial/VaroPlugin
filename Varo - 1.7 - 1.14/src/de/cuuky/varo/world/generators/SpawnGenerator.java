package de.cuuky.varo.world.generators;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import de.cuuky.varo.spawns.Spawn;
import de.cuuky.varo.spawns.spawn.SpawnType;
import de.cuuky.varo.utils.BlockUtils;
import de.cuuky.varo.version.types.Materials;

public class SpawnGenerator {

	@SuppressWarnings("deprecation")
	public SpawnGenerator(Location location, int radius, int amount, String blockId, String sideBlockId) {
		for(Spawn spawn : Spawn.getSpawnsClone()) {
			spawn.delete();
		}

		int i = 0;
		for(Location loc : generateSpawns(location, radius, amount)) {
			i++;
			Location newLoc = loc.clone();

			if(BlockUtils.isAir(newLoc.getBlock()))
				while(BlockUtils.isAir(newLoc.clone().add(0, -1, 0).getBlock()))
					newLoc = newLoc.add(0, -1, 0);
			else
				while(!BlockUtils.isAir(newLoc.getBlock()))
					newLoc = newLoc.add(0, +1, 0);

			newLoc.getBlock().setType(Material.AIR);
			newLoc.clone().add(0, -1, 0).getBlock().setType(Material.AIR);
			Materials xmat = blockId != null && !blockId.isEmpty() ? Materials.fromString(blockId) : Materials.STONE_BRICK_SLAB;
			BlockUtils.setBlock(newLoc.clone().add(1, 0, 0).getBlock(), xmat);
			BlockUtils.setBlock(newLoc.clone().add(0, 0, 1).getBlock(), xmat);
			BlockUtils.setBlock(newLoc.clone().add(-1, 0, 0).getBlock(), xmat);
			BlockUtils.setBlock(newLoc.clone().add(0, 0, -1).getBlock(), xmat);

			Materials mat = sideBlockId != null && !sideBlockId.isEmpty() ? Materials.fromString(sideBlockId) : Materials.GRASS_BLOCK;
			BlockUtils.setBlock(newLoc.clone().add(0, -2, 0).getBlock(), mat);
			BlockUtils.setBlock(newLoc.clone().add(1, -1, 0).getBlock(), mat);
			BlockUtils.setBlock(newLoc.clone().add(0, -1, 1).getBlock(), mat);
			BlockUtils.setBlock(newLoc.clone().add(-1, -1, 0).getBlock(), mat);
			BlockUtils.setBlock(newLoc.clone().add(0, -1, -1).getBlock(), mat);

			Location spawn = newLoc.getBlock().getLocation().add(0.5, -1, 0.5);
			new Spawn(SpawnType.NUMBERS, i, spawn);
		}
	}

	private List<Location> generateSpawns(Location middle, double radius, int amount) {
		double alpha = (2 * Math.PI) / amount;

		List<Location> locs = new ArrayList<>();

		for(int count = 0; count != amount; count++) {
			double beta = alpha * count;

			double x = radius * Math.cos(beta);
			double z = radius * Math.sin(beta);

			Location loc = middle.clone().add(x, 0, z);
			locs.add(loc);
		}

		return locs;
	}
}