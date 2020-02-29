package de.cuuky.varo.threads.daily.dailycheck;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.utils.varo.VaroUtils;

public class CoordsCheck extends Checker {

	@Override
	public void check() {
		if(!ConfigEntry.POST_COORDS_DAYS.isIntActivated())
			return;

		if(Main.getVaroGame().getLastCoordsPost() == null) {
			Main.getVaroGame().setLastCoordsPost(new Date());
			return;
		}

		if(new Date().after(DateUtils.addDays(Main.getVaroGame().getLastCoordsPost(), ConfigEntry.POST_COORDS_DAYS.getValueAsInt()))) {
			String post = "";
			for(VaroPlayer vp : VaroPlayer.getAlivePlayer())
				post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? VaroUtils.formatLocation(vp.getStats().getLastLocation(), "X:x Y:y Z:z in world") : "/");

			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post);
			Main.getVaroGame().setLastCoordsPost(new Date());
		}
	}
}