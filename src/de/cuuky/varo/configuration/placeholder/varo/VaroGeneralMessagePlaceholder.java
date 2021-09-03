package de.cuuky.varo.configuration.placeholder.varo;

import de.cuuky.cfw.configuration.placeholder.placeholder.ShortGMP;
import de.cuuky.varo.Main;

import java.util.function.Supplier;

public class VaroGeneralMessagePlaceholder extends ShortGMP {

	public VaroGeneralMessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier, String description, Supplier<String> s) {
		super(identifier, refreshDelay, rawIdentifier, description, s);

		Main.getCuukyFrameWork().getPlaceholderManager().registerPlaceholder(this);
	}

	public VaroGeneralMessagePlaceholder(String identifier, int refreshDelay, String description, Supplier<String> s) {
		this(identifier, refreshDelay, false, description, s);

		Main.getCuukyFrameWork().getPlaceholderManager().registerPlaceholder(this);
	}
}