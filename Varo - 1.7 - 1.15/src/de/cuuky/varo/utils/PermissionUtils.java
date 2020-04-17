package de.cuuky.varo.utils;

import java.util.ArrayList;
import java.util.HashMap;

import de.cuuky.varo.entity.player.VaroPlayer;

public final class PermissionUtils {

	private static HashMap<String, Class<?>> clazzes;

	static {
		clazzes = new HashMap<>();
		ArrayList<Class<?>> toAdd = new ArrayList<>();

		try {
			toAdd.add(Class.forName("ru.tehkode.permissions.bukkit.PermissionsEx"));
		} catch(Exception e) {}

		try {
			toAdd.add(Class.forName("me.lucko.luckperms.LuckPerms"));
			toAdd.add(Class.forName("me.lucko.luckperms.api.Contexts"));
		} catch(Exception e) {}

		for(Class<?> clazz : toAdd)
			clazzes.put(clazz.getName(), clazz);
	}

	public static String getLuckPermsPrefix(VaroPlayer player) {
		try {
			Object api = clazzes.get("me.lucko.luckperms.LuckPerms").getDeclaredMethod("getApi").invoke(null);

			Object user = api.getClass().getMethod("getUser", String.class).invoke(api, player.getUuid());
			String groupname = (String) user.getClass().getMethod("getPrimaryGroup").invoke(api);

			Object group = api.getClass().getMethod("getGroup", String.class).invoke(api, groupname);
			Object cachedData = group.getClass().getMethod("getCachedData").invoke(group);

			Object contexts = clazzes.get("me.lucko.luckperms.api.Contexts").getDeclaredMethod("allowAll").invoke(null);

			Object metadata = cachedData.getClass().getMethod("getMetaData", contexts.getClass()).invoke(cachedData, contexts);
			return (String) metadata.getClass().getMethod("getPrefix").invoke(metadata);
		} catch(Throwable e) {}

		return "";
	}

	@SuppressWarnings("unchecked")
	public static String getPermissionsExPrefix(VaroPlayer player) {
		String prefix = "";
		try {
			Object permissionUser = clazzes.get("ru.tehkode.permissions.bukkit.PermissionsEx").getDeclaredMethod("getUser", String.class).invoke(null, player.getName());
			Object[] groups = ((Object[]) permissionUser.getClass().getDeclaredMethod("getGroups").invoke(permissionUser));

			if(groups.length > 1)
				prefix = (String) groups[0].getClass().getMethod("getPrefix").invoke(groups[0]);
			else 
				prefix = (String) permissionUser.getClass().getMethod("getPrefix").invoke(permissionUser);
		} catch(Throwable e) {
			e.printStackTrace();
		}

		return prefix.replace("&", "§");
	}
}