package de.cuuky.varo.ban;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

public enum VaroPlayerBanReason {

	HACKING(ConfigSetting.BAN_HACKING_ENABLED),
	SCAMMING(ConfigSetting.BAN_SCAMMING_ENABLED),
	BAD_BEHAVIOUR(ConfigSetting.BAN_BAD_BEHAVIOUR_ENABLED),
	IDENTITY_THEFT(ConfigSetting.BAN_IDENTITY_THEFT_ENABLED),
	HARD(null),
	OTHER(ConfigSetting.BAN_OTHER_ENABLED);
	
	private ConfigSetting toggleSetting;
	private VaroPlayerBanReason(ConfigSetting toggleSetting) {
		this.toggleSetting = toggleSetting;
	}
	
	public boolean isEnabled() {
		return toggleSetting != null ? toggleSetting.getValueAsBoolean() : true;
	}
	
	public static VaroPlayerBanReason getByName(String name) {
		for(VaroPlayerBanReason reason : values())
			if(reason.toString().toLowerCase().equals(name.toLowerCase()))
				return reason;
		
		return null;
	}
}