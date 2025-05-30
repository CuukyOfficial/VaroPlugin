package de.cuuky.varo.event.events;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.cryptomorin.xseries.XMaterial;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.language.Messages;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.event.VaroEvent;
import de.cuuky.varo.event.VaroEventType;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.player.event.BukkitEventType;

public class MassRecordingVaroEvent extends VaroEvent {

    private Map<VaroPlayer, Integer> countdowns = new HashMap<>();
    private BukkitTask scheduler;
    private int timer;
    private boolean timerEnd = false;

    public MassRecordingVaroEvent() {
        super(VaroEventType.MASS_RECORDING, XMaterial.DIAMOND_SWORD, ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() == 1 ? "Lässt alle Spieler für eine Minute zusätzlich\nzu den normalen Folgen auf den Server" : "Lässt alle Spieler für " + ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() + " Minuten zusätzlich\nzu den normalen Folgen auf den Server");
    }

    public int getTimer() {
        return timer;
    }

    @Override
    public void onEnable() {
        this.countdowns.clear();
        this.timerEnd = false;

        for (VaroPlayer vp : VaroPlayer.getVaroPlayers()) {
            this.countdowns.put(vp, vp.getStats().getCountdown());
            vp.getStats().setCountdown(vp.getStats().getCountdown() + 60 * ConfigSetting.MASS_RECORDING_TIME.getValueAsInt());

            vp.setalreadyHadMassProtectionTime(false);
        }

        for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
            vp.setalreadyHadMassProtectionTime(true);
        }

        for (VaroPlayer vp : VaroPlayer.getOnlineAndAlivePlayer()) {
            vp.getStats().addSessionPlayed();
            Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.JOIN_LEAVE, vp.getName() + " ist auf dem Server und nimmt an der Massenaufnahme teil.", vp.getRealUUID());
        }

        timer = ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() * 60;

        Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() == 1 ? "DIE MASSENAUFNAHME WURDE GESTARTET UND DAUERT EINE MINUTE!" : "DIE MASSENAUFNAHME WURDE GESTARTET UND DAUERT " + ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() + " MINUTEN!");
        for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
            vp.getVersionAdapter().sendTitle("Massenaufnahme", ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() == 1 ? "Alle können für eine Minute joinen." : "Alle können für" + ConfigSetting.MASS_RECORDING_TIME.getValueAsInt() + " Minuten joinen.");

        scheduler = new BukkitRunnable() {
            @Override
            public void run() {
                if (timer < 1) {
                    timerEnd = true;
                    setEnabled(false);
                    return;
                }

                timer -= 1;
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20L);
    }

    @Override
    public void onDisable() {
        scheduler.cancel();

        for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
            if (this.timerEnd) {
                vp.getVersionAdapter().sendTitle("Ende", "Die Massenaufnahme ist zu Ende.");
                Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Die Massenaufnahme ist zu Ende.");
            } else {
                vp.getVersionAdapter().sendTitle("Ende", "Die Massenaufnahme wurde beendet.");
                Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, "Die Massenaufnahme wurde vorzeitig beendet.");
            }

            if (!vp.getStats().isSpectator() && !vp.isAdminIgnore()) {
                int countdown = this.countdowns.getOrDefault(vp, Main.getVaroGame().getPlayTime() * 60); // TODO does not work when playTime is -1
                vp.getStats().setCountdown(countdown);
                if (countdown == Main.getVaroGame().getPlayTime() * 60) {
                    if (vp.isOnline()) {
                        if (!vp.canBeKicked(ConfigSetting.NO_KICK_DISTANCE.getValueAsInt())) {
                            vp.getStats().setSessions(vp.getStats().getSessions() + 1);
                            vp.getStats().setCountdown(1);
                        } else {
                            vp.setMassRecordingKick(true);
                            Messages.PLAYER_DISCONNECT_KICK.broadcast(vp);
                            vp.onEvent(BukkitEventType.KICKED);
                            Messages.PLAYER_KICK_MASS_REC_SESSION_OVER.kick(vp);
                        }
                    }
                }
            }
        }
        this.countdowns.clear();
        this.timerEnd = false;
    }
}