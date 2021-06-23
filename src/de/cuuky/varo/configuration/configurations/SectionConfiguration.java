package de.cuuky.varo.configuration.configurations;

import de.cuuky.varo.configuration.configurations.config.ConfigSetting;

import java.util.List;

public interface SectionConfiguration {

	public String getName();

	public String getDescription();

	public String getFolder();

	public List<ConfigSetting> getEntries();

	public SectionEntry getEntry(String name);

}