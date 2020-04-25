package de.cuuky.varo.configuration.placeholder.varo;

import de.cuuky.cfw.configuration.placeholder.placeholder.PlayerMessagePlaceholder;
import de.cuuky.cfw.player.CustomPlayer;
import de.cuuky.varo.Main;
import de.cuuky.varo.entity.player.VaroPlayer;

public abstract class VaroPlayerMessagePlaceholder extends PlayerMessagePlaceholder {

	public VaroPlayerMessagePlaceholder(String identifier, int refreshDelay, String description) {
		super(identifier, refreshDelay, description);
		
		Main.getCuukyFrameWork().getPlaceholderManager().registerPlaceholder(this);
	}

	@Override
	protected String getValue(CustomPlayer player) {
		return getValue(VaroPlayer.getPlayer(player.getUUID()));
	}
	
	protected abstract String getValue(VaroPlayer player);
}