package de.varoplugin.varo.config;

import java.util.Objects;

import de.varoplugin.varo.api.config.ConfigCategory;

public class VaroConfigCategory implements ConfigCategory {
	
	private static final String FILE_EXTENSION = ".yml";
	
	public static final ConfigCategory MAIN = new VaroConfigCategory("Main", "The main config settings of this plugin"),
			SCOREBOARD = new VaroConfigCategory("Scoreboard", "Everything scoreboard related");
	
	private final String name;
	private final String description;
	private final String fileName;

	// TODO Add GUI icon
	public VaroConfigCategory(String name, String description) {
		if(name == null || description == null)
			throw new IllegalArgumentException();
		
		this.name = name;
		this.description = description;
		this.fileName = name.toLowerCase() + FILE_EXTENSION;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String getFileName() {
		return this.fileName;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		return Objects.equals(this.name, ((VaroConfigCategory) obj).name);
	}
}
