package de.cuuky.varo.threads.daily.dailycheck.checker;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.cfw.utils.LocationFormat;
import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.threads.daily.dailycheck.Checker;

public class CoordsCheck extends Checker {

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
				post = post + (post.isEmpty() ? "Liste der Koordinaten aller Spieler:\n\n" : "\n") + vp.getName() + (vp.getTeam() != null ? " (#" + vp.getTeam().getName() + ")" : "") + ": " + (vp.getStats().getLastLocation() != null ? new LocationFormat(vp.getStats().getLastLocation()).format("X:x Y:y Z:z in world") : "/");

			Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, post);
			Main.getVaroGame().setLastCoordsPost(new Date());
		}
	}
}