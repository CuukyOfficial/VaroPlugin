package de.cuuky.varo.threads.dailycheck;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.config.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.Game;
import de.cuuky.varo.logger.logger.EventLogger;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.utils.Utils;

public class CoordsCheck extends Checker {

	@Override
	public void check() {
		if(!ConfigEntry.POST_COORDS_DAYS.isIntActivated())
			return;

		if(Game.getInstance().getLastCoordsPost() == null) {
			Game.getInstance().setLastCoordsPost(new Date());
			return;
		}

		if(new Date().after(DateUtils.addDays(Game.getInstance().getLastCoordsPost(), ConfigEntry.POST_COORDS_DAYS.getValueAsInt()))) {
			String post = "";
			for(VaroPlayer vp : VaroPlayer.getAlivePlayer())
				post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? Utils.formatLocation(vp.getStats().getLastLocation(), "X:x Y:y Z:z in world") : "/");

			EventLogger.getInstance().println(LogType.ALERT, post);
			Game.getInstance().setLastCoordsPost(new Date());
		}
	}
}