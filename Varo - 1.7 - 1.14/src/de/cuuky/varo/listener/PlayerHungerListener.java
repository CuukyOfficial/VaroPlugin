package de.cuuky.varo.listener;

import de.cuuky.varo.game.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.cuuky.varo.Main;

public class PlayerHungerListener implements Listener {

	@EventHandler
	public void on(FoodLevelChangeEvent e) {
		if(!Game.getInstance().hasStarted())
			e.setFoodLevel(40);
	}
}
