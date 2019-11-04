package de.cuuky.varo.listener.helper.cancelable;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.cuuky.varo.Main;

public class VaroCancelAble {

	private static ArrayList<VaroCancelAble> cancelables;

	static {
		cancelables = new ArrayList<>();
	}

	protected Player player;
	protected CancelAbleType type;
	protected Runnable timerHook;

	public VaroCancelAble(CancelAbleType type, Player player) {
		this.player = player;
		this.type = type;

		removeOld();

		cancelables.add(this);
	}

	public VaroCancelAble(CancelAbleType type, Player player, int time) {
		this.player = player;
		this.type = type;

		removeOld();
		schedule(time);

		cancelables.add(this);
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

	private void removeOld() {
		for(int i = 0; i < cancelables.size(); i++) {
			VaroCancelAble cancelable = cancelables.get(i);
			if(cancelable.getType().equals(type) && cancelable.getPlayer().equals(player))
				cancelable.remove();
		}
	}

	public void setTimerHook(Runnable runnable) {
		this.timerHook = runnable;
	}

	public CancelAbleType getType() {
		return type;
	}

	public Player getPlayer() {
		return player;
	}

	public void remove() {
		cancelables.remove(this);
	}

	public static VaroCancelAble getCancelAble(Player player, CancelAbleType type) {
		for(VaroCancelAble able : cancelables)
			if(able.getPlayer().equals(player) && able.getType().equals(type))
				return able;

		return null;
	}
}