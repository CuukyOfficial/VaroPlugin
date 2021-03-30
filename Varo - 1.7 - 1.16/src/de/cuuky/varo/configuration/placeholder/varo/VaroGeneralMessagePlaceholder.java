package de.cuuky.varo.configuration.placeholder.varo;

import de.cuuky.cfw.configuration.placeholder.placeholder.GeneralMessagePlaceholder;
import de.cuuky.varo.Main;

public abstract class VaroGeneralMessagePlaceholder extends GeneralMessagePlaceholder {

	public VaroGeneralMessagePlaceholder(String identifier, int refreshDelay, String description) {
		this(identifier, refreshDelay, false, description);

		Main.getCuukyFrameWork().getPlaceholderManager().registerPlaceholder(this);
	}

	public VaroGeneralMessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier, String description) {
		super(identifier, refreshDelay, rawIdentifier, description);

		Main.getCuukyFrameWork().getPlaceholderManager().registerPlaceholder(this);
	}
}