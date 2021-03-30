package de.cuuky.varo.configuration.configurations;

public interface SectionEntry {

	public Object getDefaultValue();

	public Object getValue();

	public String getPath();

	public String getFullPath();

	public String[] getDescription();

	public void setValue(Object value);

	public SectionConfiguration getSection();
	
	public String[] getOldPaths();

}