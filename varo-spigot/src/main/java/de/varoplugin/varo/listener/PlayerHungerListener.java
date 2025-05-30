package de.varoplugin.varo.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.varoplugin.varo.Main;

public class PlayerHungerListener implements Listener {

	@EventHandler
	public void on(FoodLevelChangeEvent e) {
		if (!Main.getVaroGame().hasStarted())
			e.setFoodLevel(40);
	}
}
