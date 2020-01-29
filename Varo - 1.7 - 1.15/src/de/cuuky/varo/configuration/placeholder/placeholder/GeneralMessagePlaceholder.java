package de.cuuky.varo.configuration.placeholder.placeholder;

import java.util.ArrayList;

import de.cuuky.varo.configuration.placeholder.MessagePlaceholder;

public abstract class GeneralMessagePlaceholder extends MessagePlaceholder {

	private static ArrayList<GeneralMessagePlaceholder> generalPlaceholder;

	private String value;

	public GeneralMessagePlaceholder(String identifier, int refreshDelay) {
		super(identifier, refreshDelay);
		
		if(generalPlaceholder == null)
			generalPlaceholder = new ArrayList<>();

		generalPlaceholder.add(this);
	}

	public GeneralMessagePlaceholder(String identifier, int refreshDelay, boolean rawIdentifier) {
		super(identifier, refreshDelay, rawIdentifier);
		
		if(generalPlaceholder == null)
			generalPlaceholder = new ArrayList<>();

		generalPlaceholder.add(this);
	}

	@Override
	protected void refreshValues() {
		this.value = getValue();
	}

	protected abstract String getValue();
	
	public String replacePlaceholder(String message) {
		return message.replace(this.identifier, this.value == null ? this.value = getValue() : this.value);
	}
	
	public static ArrayList<GeneralMessagePlaceholder> getGeneralPlaceholder() {
		return generalPlaceholder;
	}
}