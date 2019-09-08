package de.cuuky.varo.utils;

import de.cuuky.varo.player.VaroPlayer;

public class PermissionUtils {

	@SuppressWarnings("unchecked")
	public static String getPermissionsExPrefix(VaroPlayer player) {
		try {
			Object permissionUser = Class.forName("ru.tehkode.permissions.bukkit.PermissionsEx").getDeclaredMethod("getUser", String.class).invoke(null, player.getName());
			Object group = ((Object[]) permissionUser.getClass().getDeclaredMethod("getGroups").invoke(permissionUser))[0];

			return (String) group.getClass().getMethod("getPrefix").invoke(group);
		} catch(Throwable e) {}

		return "";
	}

	public static String getLuckPermsPrefix(VaroPlayer player) {
		try {
			Object api = Class.forName("me.lucko.luckperms.LuckPerms").getDeclaredMethod("getApi").invoke(null);

			Object user = api.getClass().getMethod("getUser", String.class).invoke(api, player.getUuid());
			String groupname = (String) user.getClass().getMethod("getPrimaryGroup").invoke(api);

			Object group = api.getClass().getMethod("getGroup", String.class).invoke(api, groupname);
			Object cachedData = group.getClass().getMethod("getCachedData").invoke(group);

			Object contexts = Class.forName("me.lucko.luckperms.api.Contexts").getDeclaredMethod("allowAll").invoke(null);

			Object metadata = cachedData.getClass().getMethod("getMetaData", contexts.getClass()).invoke(cachedData, contexts);
			return (String) metadata.getClass().getMethod("getPrefix").invoke(metadata);
		} catch(Throwable e) {}

		return "";
	}
}
