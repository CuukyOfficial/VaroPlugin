package de.varoplugin.varo.game.start;

import com.cryptomorin.xseries.XSound;
import de.varoplugin.cfw.version.VersionUtils;
import de.varoplugin.varo.config.language.Contexts;
import de.varoplugin.varo.config.language.Messages;
import de.varoplugin.varo.game.VaroGame;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.player.VaroPlayer;

public class SuroStart extends AbstractStartSequence {

	private final int titleSize;

	public SuroStart(VaroGame game) {
		super(game);
		this.titleSize = Messages.GAME_START_SURO_TITLE.size() - 1;
		this.countdown = this.titleSize;
	}

	@Override
	public void start() {
		for (VaroPlayer vp : VaroPlayer.getOnlineAndAlivePlayer()) {
			Player player = vp.getPlayer();
			player.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 3));
			player.setGameMode(GameMode.ADVENTURE);
			vp.cleanUpPlayer();
		}

		this.task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
			VersionUtils.getVersionAdapter().getOnlinePlayers().stream().findFirst().ifPresent(player -> player.getWorld().setTime(1000));

			for (VaroPlayer vp : VaroPlayer.getOnlineAndAlivePlayer()) {
				Contexts.PlayerContext context = new Contexts.PlayerContext(vp); // Setting the context manually forces the default language
				String title = Messages.GAME_START_SURO_TITLE.value(this.titleSize - this.countdown, context);
				if (StringUtils.isNotBlank(title))
					VersionUtils.getVersionAdapter().sendTitle(vp.getPlayer(), title, "");
			}

			if (this.countdown == 0) {
				for (VaroPlayer vp : VaroPlayer.getOnlineAndAlivePlayer())
					vp.getPlayer().playSound(vp.getPlayer().getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.get(), 1, 1);

				this.game.start();
				return;
			}
			this.countdown--;
		}, 0L, 20L);
	}

	@Override
	public void abort() {
		super.abort();

		for (VaroPlayer vp : VaroPlayer.getOnlinePlayer())
			vp.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
	}
}