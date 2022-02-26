package de.cuuky.varo.event;

import de.cuuky.cfw.version.VersionUtils;
import de.cuuky.varo.Varo;
import org.bukkit.entity.Player;

public class PoisonWaterVaroEvent extends VaroScheduledEvent {

    public PoisonWaterVaroEvent(Varo varo) {
        super(varo, VaroEventType.POISON_WATER, 20);
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