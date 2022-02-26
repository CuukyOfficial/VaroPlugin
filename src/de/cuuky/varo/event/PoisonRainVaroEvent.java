package de.cuuky.varo.event;

import de.cuuky.cfw.utils.BlockUtils;
import de.cuuky.cfw.version.VersionUtils;
import org.bukkit.entity.Player;

public class PoisonRainVaroEvent extends VaroScheduledEvent {

	public PoisonRainVaroEvent(EventInformationHolder info) {
		super(info, 20);
	}

    @Override
    void onDisable() {
    }

    @Override
    void onSchedule() {
        playerLoop: for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers()) {
            if (p.getWorld().hasStorm() && !p.getLocation().getBlock().getBiome().toString().contains("SAVANNA")) {
                for (int i = p.getLocation().getBlockY(); i < p.getWorld().getMaxHeight(); i++)
                    if (!BlockUtils.isAir(p.getWorld().getBlockAt(p.getLocation().getBlockX(), i, p.getLocation().getBlockZ())))
                        continue playerLoop;

                p.damage(0.75);
            }
        }
    }
}