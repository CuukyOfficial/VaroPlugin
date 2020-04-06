package de.cuuky.varo.game.suro;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.cuuky.varo.Main;
import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.game.state.GameState;
import de.cuuky.varo.listener.helper.cancelable.CancelAbleType;
import de.cuuky.varo.listener.helper.cancelable.VaroCancelAble;
import de.cuuky.varo.version.types.Sounds;

public class SuroStart {

	private int sched;
	private ArrayList<String> titles;

	public SuroStart() {
		titles = new ArrayList<>();

		titles.add("§a%name%");
		titles.add("§6...du bist gestrandet...");
		titles.add("§c...auf einer Insel...");
		titles.add("§6...genau so wie...");
		titles.add("§c%players% weitere Spieler auch!");
		titles.add("§6Bist du bereit, §a%name%§6?");
		titles.add("§aJa?");
		titles.add("§6dann viel Glück bei...");
		titles.add("§cMINECRAFT " + ConfigSetting.PROJECT_NAME.getValueAsString().toUpperCase() + "!");
		titles.add("§cWach auf!");
		titles.add("§c10!");
		titles.add("");
		titles.add("");
		titles.add("");
		titles.add("");
		titles.add("§c5!");
		titles.add("§c4!");
		titles.add("§c3!");
		titles.add("§c2!");
		titles.add("§c1!");
		titles.add("§cGO!");

		start(60, 0, false);
	}

	public void start(int delay, int start, boolean ignore) {
		sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {

			int i = start;

			@Override
			public void run() {
				if (titles.size() - 11 == i && !ignore) {
					Bukkit.getScheduler().cancelTask(sched);
					start(20, i, true);
				}

				if (i >= titles.size()) {
					Bukkit.getScheduler().cancelTask(sched);
					Main.getVaroGame().setGamestate(GameState.STARTED);
					for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
						vp.getPlayer().playSound(vp.getPlayer().getLocation(), Sounds.NOTE_PLING.bukkitSound(), 1, 1);
						vp.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
						VaroCancelAble.removeCancelAble(vp, CancelAbleType.FREEZE);
						VaroCancelAble.removeCancelAble(vp, CancelAbleType.MUTE);
						VaroCancelAble.removeCancelAble(vp, CancelAbleType.PROTECTION);
					}
					return;
				}

				for (VaroPlayer vp : VaroPlayer.getOnlinePlayer()) {
					new VaroCancelAble(CancelAbleType.FREEZE, vp);
					new VaroCancelAble(CancelAbleType.MUTE, vp);
					new VaroCancelAble(CancelAbleType.PROTECTION, vp);

					vp.cleanUpPlayer();
					vp.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 9999, 3));
					if (!titles.get(i).isEmpty())
						vp.getNetworkManager().sendTitle(titles.get(i).replace("%name%", vp.getName())
								.replace("%players%", String.valueOf(VaroPlayer.getAlivePlayer().size())), "");
				}

				i++;
			}
		}, 1, delay);
	}
}