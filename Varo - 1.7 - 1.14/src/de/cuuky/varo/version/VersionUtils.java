package de.cuuky.varo.version;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VersionUtils {

	private static String nmsClass;
	private static BukkitVersion version;

	static {
		nmsClass = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		version = BukkitVersion.getVersion(nmsClass);
	}

	public static ArrayList<Player> getOnlinePlayer() {
		ArrayList<Player> list = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers())
			list.add(p);

		return list;
	}

	public static Class<?> getChatSerializer() {
		try {
			return Class.forName(VersionUtils.getNmsClass() + ".IChatBaseComponent$ChatSerializer");
		} catch(ClassNotFoundException e) {
			try {
				return Class.forName(VersionUtils.getNmsClass() + ".ChatSerializer");
			} catch(ClassNotFoundException e1) {}
		}

		return null;
	}

	public static String getNmsClass() {
		return nmsClass;
	}

	public static BukkitVersion getVersion() {
		return version;
	}
}