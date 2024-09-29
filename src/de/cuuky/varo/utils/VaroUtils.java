package de.cuuky.varo.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.RegisteredServiceProvider;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.UserManager;

public final class VaroUtils {
    
    private static final String GAMERULE_DO_DAYLIGHT_CYCLE = "doDaylightCycle";
    
    private static Object luckPermsUserManager;
	
	public static void updateWorldTime() {
	    int time = ConfigSetting.ALWAYS_TIME.getValueAsInt();

	    for (World world : Bukkit.getWorlds())
	        if (!ConfigSetting.ALWAYS_TIME.isIntActivated() || (Main.getVaroGame().hasStarted() && !ConfigSetting.ALWAYS_TIME_USE_AFTER_START.getValueAsBoolean()))
	            world.setGameRuleValue(GAMERULE_DO_DAYLIGHT_CYCLE, "true");
	        else {
	            world.setGameRuleValue(GAMERULE_DO_DAYLIGHT_CYCLE, "false");
	            if (world.getTime() != time)
	                world.setTime(time);
	            world.setStorm(false);
	            world.setThundering(false);
	        }
	}

	public static void doRandomTeam(int teamSize) {
		int maxNameLength = ConfigSetting.TEAM_MAX_NAME_LENGTH.getValueAsInt();
        List<VaroPlayer> random = VaroPlayer.getVaroPlayers().stream()
            .filter(pl -> pl.getTeam() == null && !pl.getStats().isSpectator()).collect(Collectors.toList());
        Collections.shuffle(random);

        for (int i = 0; i < random.size(); i += teamSize) {
            int actualSize = Math.min(i + teamSize, random.size());
            Collection<VaroPlayer> member = random.subList(i, actualSize);
            if (member.size() < teamSize)
                member.forEach(m -> m.sendMessage(ConfigMessages.VARO_COMMANDS_RANDOMTEAM_NO_PARTNER));

            // name
            String name = member.stream().map(m -> m.getName()
                .substring(0, Math.min(m.getName().length(), maxNameLength / member.size()))).collect(Collectors.joining());

            // add
            VaroTeam team = new VaroTeam(name);
            member.forEach(team::addMember);
        }
	}
	
	public static boolean isNotSolidTerrain(Block block) {
	    Material material = block.getType();
	    return !material.isSolid() && !material.isOccluding();
	}
	
	public static boolean isNotSolidTerrainOrLiquid(Block block) {
        return isNotSolidTerrain(block) && !block.isLiquid();
    }

	private static Object getLuckPermsPlayerAdapter() {
	    if (luckPermsUserManager != null)
	        return luckPermsUserManager;
	    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
	    return provider != null ? luckPermsUserManager = provider.getProvider().getUserManager() : null;
	}
	
	public static String getLuckPermsPrefix(VaroPlayer player) {
        UserManager luckPerms = (UserManager) getLuckPermsPlayerAdapter();
	    if (luckPerms == null)
	        return "";
	    String prefix = luckPerms.getUser(player.getRealUUID()).getCachedData().getMetaData().getPrefix();
	    return prefix == null ? "" : prefix;
	}

	public static String getLuckPermsSuffix(VaroPlayer player) {
	    UserManager luckPerms = (UserManager) getLuckPermsPlayerAdapter();
        if (luckPerms == null)
            return "";
        String suffix = luckPerms.getUser(player.getRealUUID()).getCachedData().getMetaData().getSuffix();
        return suffix == null ? "" : suffix;
    }
}