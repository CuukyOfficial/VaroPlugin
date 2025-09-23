package de.varoplugin.varo.game.start;

import de.varoplugin.varo.serialize.identifier.VaroSerializeField;
import de.varoplugin.varo.serialize.identifier.VaroSerializeable;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import io.github.almightysatan.slams.PlaceholderResolver;

public class ProtectionTime implements VaroSerializeable {

    @VaroSerializeField(path = "borderDecrease")
	private int protectionTimer;

    private BukkitTask scheduler;
    
    public ProtectionTime() {}

	public ProtectionTime(int timer) {
        this.protectionTimer = timer;
		startTimer();
	}

	public void startTimer() {
		if (this.protectionTimer <= 0)
            return;

        this.scheduler = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (!Main.getVaroGame().isRunning()) {
                this.scheduler.cancel();
                return;
            }

            if (this.protectionTimer == 0) {
                Messages.PROTECTION_END.broadcast();
                Main.getVaroGame().setProtection(null);
                return;
            } else if (this.protectionTimer % ConfigSetting.STARTPERIOD_PROTECTIONTIME_BROADCAST_INTERVAL.getValueAsInt() == 0) {
                Messages.PROTECTION_UPDATE.broadcast(PlaceholderResolver.builder().constant("protection-minutes", getCountdownMin(protectionTimer))
                        .constant("protection-seconds", getCountdownSec(protectionTimer)).build());
            }

            this.protectionTimer--;
        }, 0L, 20L);
	}
    
    public void cancel() {
        this.scheduler.cancel();
    }

	public String getCountdownMin(int sec) {
		int min = sec / 60;
		return (min < 10) ? "0" + min : String.valueOf(min);
	}

	public String getCountdownSec(int sec) {
		sec = sec % 60;
		return (sec < 10) ? "0" + sec : String.valueOf(sec);
	}

	/**
	 * Returns the protection timer
	 * 
	 * @return the protection timer
	 */
	public int getProtectionTimer() {
		return this.protectionTimer;
	}

    @Override
    public void onDeserializeEnd() {}

    @Override
    public void onSerializeStart() {}
}
