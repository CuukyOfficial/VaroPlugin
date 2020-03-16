package de.cuuky.varo.version;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;

public class VersionUtils {

	private static String nmsClass;
	private static BukkitVersion version;
	private static Object spigot;
	private static Class<?> chatSerializer;

	static {
		nmsClass = "net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		version = BukkitVersion.getVersion(nmsClass);
		try {
			spigot = Bukkit.getServer().getClass().getDeclaredMethod("spigot").invoke(Bukkit.getServer());
		} catch(Exception e) {}

		try {
			chatSerializer = Class.forName(VersionUtils.getNmsClass() + ".IChatBaseComponent$ChatSerializer");
		} catch(ClassNotFoundException e) {
			try {
				chatSerializer = Class.forName(VersionUtils.getNmsClass() + ".ChatSerializer");
			} catch(ClassNotFoundException e1) {}
		}
	}

	public static Class<?> getChatSerializer() {
		return chatSerializer;
	}

	public static double getHearts(Player player) {
		return ((Damageable) player).getHealth();
	}

	public static String getNmsClass() {
		return nmsClass;
	}

	public static Object getSpigot() {
		return spigot;
	}

	public static ArrayList<Player> getOnlinePlayer() {
		ArrayList<Player> list = new ArrayList<Player>();
		for(Player p : Bukkit.getOnlinePlayers())
			list.add(p);

		return list;
	}

	public static BukkitVersion getVersion() {
		return version;
	}
}