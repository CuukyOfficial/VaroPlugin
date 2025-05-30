package de.varoplugin.varo.threads.daily.checks;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.varoplugin.cfw.location.LocationFormat;
import de.varoplugin.cfw.location.SimpleLocationFormat;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.logger.logger.EventLogger.LogType;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.threads.daily.Checker;

public class CoordsCheck extends Checker {
    
    private static final LocationFormat LOCATION_FORMAT = new SimpleLocationFormat("X:x Y:y Z:z in world");

	@Override
	public void check() {
		if (!ConfigSetting.POST_COORDS_DAYS.isIntActivated())
			return;

		if (Main.getVaroGame().getLastCoordsPost() == null) {
			Main.getVaroGame().setLastCoordsPost(new Date());
			return;
		}

		if (new Date().after(DateUtils.addDays(Main.getVaroGame().getLastCoordsPost(), ConfigSetting.POST_COORDS_DAYS.getValueAsInt()))) {
			String post = "";
			for (VaroPlayer vp : VaroPlayer.getAlivePlayer())
				post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? LOCATION_FORMAT.format(vp.getStats().getLastLocation()) : "/");

			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post);
			Main.getVaroGame().setLastCoordsPost(new Date());
		}
	}
}