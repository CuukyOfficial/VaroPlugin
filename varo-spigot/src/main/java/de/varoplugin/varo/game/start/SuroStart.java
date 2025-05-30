package de.varoplugin.varo.game.start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.cryptomorin.xseries.XSound;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.game.LobbyItem;
import de.varoplugin.varo.listener.helper.cancelable.CancelableType;
import de.varoplugin.varo.listener.helper.cancelable.VaroCancelable;
import de.varoplugin.varo.player.VaroPlayer;

public class SuroStart {

	private BukkitTask sched;
	private List<String> titles;

	public static final ArrayList<String> DEFAULT_TITLES = new ArrayList<>(Arrays.asList(new String[] {"&a%name%", "&6...du bist gestrandet...", "&c...auf einer Insel...", "&6...genau so wie...", "&c%players% weitere Spieler auch!", "&aJa?", "&6dann viel Glück bei...", "&cMINECRAFT SURO!", "&cWach auf!", "&c10!", "", "", "", "", "&c5!", "&c4!", "&c3!", "&c2!", "&c1!", "&cGO!"}));

	@SuppressWarnings("unchecked")
	public SuroStart() {
		titles = (List<String>) ConfigSetting.INTRO_INTRO_LINES.getValue();
		start(60, 0, false);
	}

	public void start(int delay, int start, boolean ignore) {
		LobbyItem.removeHooks();
		sched = new BukkitRunnable() {

			int i = start;
			@Override
			public void run() {
				if (titles.size() - 11 == i && !ignore) {
					sched.cancel();
					start(20, i, true);
				}

				if (i >= titles.size()) {
					sched.cancel();

					for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
						vp.getPlayer().playSound(vp.getPlayer().getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.get(), 1, 1);
						vp.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
						VaroCancelable.removeCancelable(vp, CancelableType.FREEZE);
						VaroCancelable.removeCancelable(vp, CancelableType.MUTE);
						VaroCancelable.removeCancelable(vp, CancelableType.PROTECTION);
					}

					Main.getVaroGame().start();
					return;
				}

				for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
					new VaroCancelable(CancelableType.FREEZE, vp);
					new VaroCancelable(CancelableType.MUTE, vp);
					new VaroCancelable(CancelableType.PROTECTION, vp);

					vp.cleanUpPlayer();
					vp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 3));
					if (!titles.get(i).isEmpty())
						vp.getVersionAdapter().sendTitle(ChatColor.translateAlternateColorCodes('&', titles.get(i).replace("%name%", vp.getName()).replace("%players%", String.valueOf(VaroPlayer.getAlivePlayer().size()))), "");
				}

				i++;
			}
		}.runTaskTimer(Main.getInstance(), 1, delay);
	}
}