package de.cuuky.varo.gui;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;
import org.bukkit.entity.Player;

import de.cuuky.cfw.menu.SuperInventory;

public abstract class VaroSuperInventory extends SuperInventory {

	public VaroSuperInventory(String title, Player opener, int size, boolean homePage) {
		super(title.length() > 32 ? title.substring(0, 32) : title, opener, size, homePage);

		this.animations = ConfigSetting.GUI_INVENTORY_ANIMATIONS.getValueAsBoolean();
		this.fillInventory = ConfigSetting.GUI_FILL_INVENTORY.getValueAsBoolean();
	}
}