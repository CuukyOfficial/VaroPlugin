package de.varoplugin.varo.configuration.configurations;

import java.util.List;

import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;

public interface SectionConfiguration {

	public String getName();

	public String getDescription();

	public String getFolder();

	public List<ConfigSetting> getEntries();

	public SectionEntry getEntry(String name);

}