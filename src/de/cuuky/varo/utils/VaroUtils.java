package de.cuuky.varo.utils;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.configuration.configurations.language.languages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.team.VaroTeam;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class VaroUtils {

	private static BukkitTask worldToTimeID;

	public static void setWorldToTime() {
		if (!ConfigSetting.ALWAYS_TIME.isIntActivated())
			return;

		if (worldToTimeID != null)
			worldToTimeID.cancel();

		worldToTimeID = new BukkitRunnable() {

			final int time = ConfigSetting.ALWAYS_TIME.getValueAsInt();

			@Override
			public void run() {
				if (Main.getVaroGame().hasStarted() && !ConfigSetting.ALWAYS_TIME_USE_AFTER_START.getValueAsBoolean()) {
					worldToTimeID.cancel();
					return;
				}

				for (World world : Bukkit.getWorlds()) {
					world.setTime(time);
					world.setThundering(false);
					world.setStorm(false);
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, 40);
	}

	public static void doRandomTeam(int teamSize) {
        List<VaroPlayer> random = VaroPlayer.getVaroPlayers().stream()
            .filter(pl -> pl.getTeam() == null && !pl.getStats().isSpectator()).collect(Collectors.toList());
        Collections.shuffle(random);

        for (int i = 0; i < random.size(); i += teamSize) {
            int actualSize = Math.min(i + teamSize, random.size() - 1);
            Collection<VaroPlayer> member = random.subList(i, actualSize);
            if (actualSize < teamSize)
                member.forEach(m -> m.sendMessage(ConfigMessages.VARO_COMMANDS_RANDOMTEAM_NO_PARTNER));

            // name
            String name = member.stream().map(m -> m.getName()
                .substring(0, m.getName().length() / actualSize)).collect(Collectors.joining());

            // add
            VaroTeam team = new VaroTeam(name);
            member.forEach(team::addMember);
        }
	}
}