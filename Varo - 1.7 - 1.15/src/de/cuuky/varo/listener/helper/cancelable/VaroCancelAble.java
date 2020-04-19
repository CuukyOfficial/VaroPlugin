package de.cuuky.varo.listener.helper.cancelable;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;

public class VaroCancelAble {

	private static ArrayList<VaroCancelAble> cancelables;

	static {
		cancelables = new ArrayList<>();
	}

	protected VaroPlayer player;
	protected Runnable timerHook;
	protected CancelAbleType type;

	public VaroCancelAble(CancelAbleType type, VaroPlayer player) {
		this.player = player;
		this.type = type;

		removeOld();

		cancelables.add(this);
	}

	public VaroCancelAble(CancelAbleType type, VaroPlayer player, int time) {
		this.player = player;
		this.type = type;

		removeOld();
		schedule(time);

		cancelables.add(this);
	}

	private void remove() {
		cancelables.remove(this);
	}

	private void removeOld() {
		removeCancelAble(player, type);
	}

	private void schedule(int time) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				timerHook.run();
				remove();
			}
		}, time * 20);
	}

	public VaroPlayer getPlayer() {
		return player;
	}

	public CancelAbleType getType() {
		return type;
	}

	public void setTimerHook(Runnable runnable) {
		this.timerHook = runnable;
	}

	public static VaroCancelAble getCancelAble(VaroPlayer player, CancelAbleType type) {
		for (VaroCancelAble able : cancelables)
			if (able.getPlayer().equals(player) && able.getType().equals(type))
				return able;

		return null;
	}

	public static void removeCancelAble(VaroPlayer player, CancelAbleType type) {
		for (int i = 0; i < cancelables.size(); i++) {
			VaroCancelAble able = cancelables.get(i);
			if (able.getPlayer().equals(player) && able.getType().equals(type)) {
				able.remove();
				i--;
			}
		}

	}
}