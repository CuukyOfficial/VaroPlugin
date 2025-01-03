package de.cuuky.varo.configuration.placeholder.varo;

import de.cuuky.cfw.configuration.placeholder.placeholder.ShortOMP;
import de.cuuky.varo.Main;
import de.cuuky.varo.player.VaroPlayer;

import java.util.function.Function;

public class VaroPlayerMessagePlaceholder extends ShortOMP<VaroPlayer> {

	public VaroPlayerMessagePlaceholder(String identifier, int refreshDelay, String description, Function<VaroPlayer, String> s) {
		super(identifier, refreshDelay, description, s);

		Main.getCuukyFrameWork().getPlaceholderManager().registerPlaceholder(this);
	}
}