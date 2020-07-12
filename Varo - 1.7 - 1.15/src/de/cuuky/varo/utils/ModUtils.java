package de.cuuky.varo.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.magmafoundation.magma.api.PlayerAPI;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;

public class ModUtils {

	public static void checkForIllegalMods(Player player) {

		boolean kickPlayer = false;
		String modList = PlayerAPI.getModlist(player);
		ArrayList<String> usedMods = new ArrayList<String>();
		for(String mod : Main.getDataManager().getListManager().getBlockedMods().getAsList()) {
			if(modList.toLowerCase().contains(mod.toLowerCase())) {
				kickPlayer = true;
				usedMods.add(mod);
			}
			if(kickPlayer) {
				String illegalMods = JavaUtils.getArgsToString(usedMods, "§7, " + Main.getColorCode());
				if(!player.hasPermission("varo.alwaysjoin")) {
					player.kickPlayer(Main.getPrefix() + ConfigMessages.MODS_BLOCKED_MODS_KICK.getValue(VaroPlayer.getPlayer(player)).replace("%mods%", illegalMods));
				}
				for(Player p : Bukkit.getOnlinePlayers()) {
					VaroPlayer.getPlayer(p).sendMessage(Main.getPrefix() + ConfigMessages.MODS_BLOCKED_MODS_BROADCAST.getValue().replace("%mods%", illegalMods).replace("%player%", player.getName()));
				}
			}
		}

	}
}
