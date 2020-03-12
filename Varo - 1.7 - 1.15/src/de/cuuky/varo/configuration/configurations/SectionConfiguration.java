package de.cuuky.varo.configuration.configurations;

import java.util.ArrayList;

public interface SectionConfiguration {

	public String getName();
	
	public String getDescription();
	
	public ArrayList<SectionEntry> getEntries();
	
}