package de.varoplugin.varo.listener.helper.cancelable;

import java.util.ArrayList;

import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.player.VaroPlayer;

// TODO this belongs in the trash and should be replaced by a system that is able to freeze/mute/protect all players at once
public class VaroCancelable {

	private static ArrayList<VaroCancelable> cancelables;

	static {
		cancelables = new ArrayList<>();
	}

	protected VaroPlayer player;
	protected Runnable timerHook;
	protected CancelableType type;

	public VaroCancelable(CancelableType type, VaroPlayer player) {
		this.player = player;
		this.type = type;

		removeOld();

		cancelables.add(this);
	}

	public VaroCancelable(CancelableType type, VaroPlayer player, int time) {
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
		removeCancelable(player, type);
	}

	private void schedule(int time) {
		new BukkitRunnable() {
			@Override
			public void run() {
				timerHook.run();
				remove();
			}
		}.runTaskLater(Main.getInstance(), time * 20);
	}

	public VaroPlayer getPlayer() {
		return player;
	}

	public CancelableType getType() {
		return type;
	}

	public void setTimerHook(Runnable runnable) {
		this.timerHook = runnable;
	}

	public static VaroCancelable getCancelable(VaroPlayer player, CancelableType type) {
		for (VaroCancelable able : cancelables)
			if (able.getPlayer().equals(player) && able.getType().equals(type))
				return able;

		return null;
	}

	public static void removeCancelable(VaroPlayer player, CancelableType type) {
		for (int i = 0; i < cancelables.size(); i++) {
			VaroCancelable able = cancelables.get(i);
			if (able.getPlayer().equals(player) && able.getType().equals(type)) {
				able.remove();
				i--;
			}
		}

	}
}