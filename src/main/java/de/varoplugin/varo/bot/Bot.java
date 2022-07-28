package de.varoplugin.varo.bot;

import de.varoplugin.varo.VaroPlugin;

public interface Bot {

	void init(VaroPlugin varo);

	void shutdown();
	
	boolean isEnabled();
}