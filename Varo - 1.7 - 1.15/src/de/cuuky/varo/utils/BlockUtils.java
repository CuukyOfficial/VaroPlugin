package de.cuuky.varo.utils;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Material;
import org.bukkit.block.Block;

import de.cuuky.varo.version.BukkitVersion;
import de.cuuky.varo.version.VersionUtils;
import de.cuuky.varo.version.types.Materials;

public final class BlockUtils {

	private BlockUtils() {}

	private static boolean isGrass(Material type) {
		if(!type.toString().contains("GRASS"))
			return false;

		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_11))
			return !type.toString().equals("GRASS");
		else
			return !type.toString().contains("BLOCK");
	}

	public static boolean isAir(Block block) {
		Material type = block.getType();

		return isGrass(type) || type.toString().endsWith("AIR") || Materials.POPPY.parseMaterial() == type || type == Materials.SUNFLOWER.parseMaterial() || type == Materials.LILY_PAD.parseMaterial() || type.name().contains("LEAVES") || type.name().contains("WOOD") || type == Materials.SNOW.parseMaterial() || type.name().contains("GLASS") || type == Materials.VINE.parseMaterial();
	}

	public static boolean isSame(Materials mat, Block block) {
		if(mat.getData() == block.getData() && mat.parseMaterial().equals(block.getType()))
			return true;

		return false;
	}

	public static void setBlock(Block block, Materials mat) {
		block.setType(mat.parseMaterial());

		if(!VersionUtils.getVersion().isHigherThan(BukkitVersion.ONE_11)) {
			try {
				block.getClass().getDeclaredMethod("setData", byte.class).invoke(block, (byte) mat.getData());
			} catch(IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
	}
}