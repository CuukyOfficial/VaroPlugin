package de.cuuky.varo.threads.dailycheck;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.player.VaroPlayer;
import de.cuuky.varo.utils.LocationFormatter;

public class CoordsCheck extends Checker {

	@Override
	public void check() {
		if(!ConfigEntry.POST_COORDS_DAYS.isIntActivated())
			return;

		if(Main.getGame().getLastCoordsPost() == null) {
			Main.getGame().setLastCoordsPost(new Date());
			return;
		}

		if(new Date().after(DateUtils.addDays(Main.getGame().getLastCoordsPost(), ConfigEntry.POST_COORDS_DAYS.getValueAsInt()))) {
			String post = "";
			for(VaroPlayer vp : VaroPlayer.getAlivePlayer()) 
				post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? new LocationFormatter("X:x Y:y Z:z in world").format(vp.getStats().getLastLocation()) : "/");

			Main.getLoggerMaster().getEventLogger().println(LogType.INFO, post);
			Main.getGame().setLastCoordsPost(new Date());
		}
	}
}
