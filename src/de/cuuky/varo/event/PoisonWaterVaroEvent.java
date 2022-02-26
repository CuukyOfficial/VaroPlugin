package de.cuuky.varo.event;

import de.cuuky.cfw.version.VersionUtils;
import org.bukkit.entity.Player;

public class PoisonWaterVaroEvent extends VaroScheduledEvent {

    public PoisonWaterVaroEvent(EventInformationHolder info) {
        super(info, 20);
    }

    @Override
    void onDisable() {
    }

    @Override
    void onSchedule() {
        for (Player p : VersionUtils.getVersionAdapter().getOnlinePlayers()) {
            if (p.getLocation().getBlock().getType().toString().contains("WATER"))
                p.damage(0.75);
        }
    }
}