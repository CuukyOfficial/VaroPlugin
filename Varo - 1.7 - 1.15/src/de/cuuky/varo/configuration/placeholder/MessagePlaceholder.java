package de.cuuky.varo.configuration.placeholder;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import de.cuuky.varo.Main;

public abstract class MessagePlaceholder {

	private static ArrayList<MessagePlaceholder> placeholder;

	static {
		placeholder = new ArrayList<>();

		new MessagePlaceholderLoader();
		startRefreshing();
	}

	protected String identifier;
	protected int refreshDelay, refreshCount;

	public MessagePlaceholder(String identifier, int refreshDelay) {
		this(identifier, refreshDelay, false);
	}

	public MessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier) {
		if(rawIdentifier)
			this.identifier = identifier;
		else
			this.identifier = "%" + identifier + "%";
		
		this.refreshDelay = refreshDelay;
		this.refreshCount = refreshDelay;

		placeholder.add(this);
	}

	protected abstract void refreshValues();

	public boolean containsPlaceholder(String message) {
		return message.contains(identifier);
	}

	public int getRefreshDelay() {
		return this.refreshDelay;
	}

	public int getRefreshCount() {
		return this.refreshCount;
	}

	public void setRefreshCount(int refreshCount) {
		this.refreshCount = refreshCount;
	}

	private static void startRefreshing() {
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				for(MessagePlaceholder mp : placeholder) {
					if(mp.getRefreshDelay() < 1)
						continue;

					mp.setRefreshCount(mp.getRefreshCount() - 1);
					
					if(mp.getRefreshCount() == 0) {
						mp.refreshValues();
						mp.setRefreshCount(mp.getRefreshDelay());
					}
				}
			}
		}, 20, 20);
	}

	public static ArrayList<MessagePlaceholder> getPlaceholder() {
		return placeholder;
	}
}