package de.cuuky.varo.configuration.configurations;

import java.util.ArrayList;

public interface SectionConfiguration {

	public String getName();

	public String getDescription();

	public String getFolder();

	public ArrayList<SectionEntry> getEntries();

	public SectionEntry getEntry(String name);

}