package de.cuuky.varo.configuration.configurations.messages.language.languages;

import de.cuuky.varo.entity.player.VaroPlayer;

public interface DefaultLanguage extends LoadableMessage {
	
	public String getValue();
	public String getValue(VaroPlayer languageHolder);
	public String getValue(VaroPlayer languageHolder, VaroPlayer replace);
	public void setMessage(String message);

}