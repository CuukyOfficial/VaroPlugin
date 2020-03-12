package de.cuuky.varo.configuration.configurations;

public interface SectionEntry {
	
	public Object getDefaultValue();

	public String getPath();
	
	public String[] getDescription();
	
	public void setValue(Object value);
	
	public SectionConfiguration getSection();

}