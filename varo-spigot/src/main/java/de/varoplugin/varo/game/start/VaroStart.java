/*
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.varoplugin.varo.game.start;

import com.cryptomorin.xseries.XSound;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.VaroGame;
import de.varoplugin.varo.player.VaroPlayer;
import io.github.almightysatan.slams.Placeholder;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class VaroStart extends AbstractStartSequence {

	public VaroStart(VaroGame game) {
		super(game);
		this.countdown = ConfigSetting.STARTCOUNTDOWN.getValueAsInt();
	}

	@Override
	public void start() {
		this.task = Main.scheduler().runTimer(() -> {
			VersionUtils.getVersionAdapter().getOnlinePlayers().stream().findFirst().ifPresent(player -> player.getWorld().setTime(1000));

			if (countdown != 0)
				Messages.GAME_START_VARO_COUNTDOWN.broadcast(Placeholder.constant("start-countdown", String.valueOf(this.countdown)));

			if (countdown == ConfigSetting.STARTCOUNTDOWN.getValueAsInt() || countdown == 1) {
				for (VaroPlayer pl1 : VaroPlayer.getOnlinePlayer()) {
					if (pl1.getStats().isSpectator())
						continue;

					Player pl = pl1.getPlayer();
					if (pl == null) // might have left in the meantime
						continue;
					Main.scheduler().executeFor(pl, () -> pl.setGameMode(GameMode.ADVENTURE));
					pl1.cleanUpPlayer();
				}
			}

			if (countdown == 5 || countdown == 4 || countdown == 3 || countdown == 2 || countdown == 1) {
				for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
					if (vp.getStats().isSpectator())
						continue;

					Player pl = vp.getPlayer();
					if (pl == null)
						continue;
					Main.scheduler().executeFor(pl, () -> pl.playSound(pl.getLocation(), XSound.BLOCK_NOTE_BLOCK_BASEDRUM.get(), 1, 1));

					String title = Messages.GAME_START_VARO_TITLE.value(vp ,Placeholder.constant("start-countdown", String.valueOf(this.countdown)));
					String subtitle = Messages.GAME_START_VARO_SUBTITLE.value(vp, Placeholder.constant("start-countdown", String.valueOf(this.countdown)));
					if (!title.isEmpty() || !subtitle.isEmpty())
						vp.getVersionAdapter().sendTitle(title, subtitle);
				}
			}

			if (countdown == 0) {
				World mainWorld = Main.getVaroGame().getVaroWorldHandler().getMainWorld().getWorld();
				Main.scheduler().executeAt(mainWorld.getSpawnLocation(), () -> mainWorld.strikeLightningEffect(mainWorld.getSpawnLocation()));
				Messages.GAME_START_VARO_BROADCAST.broadcast();

				this.game.start();
				return;
			}

			countdown--;
		}, 0L, 20L);
	}
}