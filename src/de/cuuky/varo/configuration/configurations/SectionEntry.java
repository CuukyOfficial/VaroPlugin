package de.cuuky.varo.configuration.configurations;

public interface SectionEntry {

    Object getDefaultValue();

    Object getValue();

    String getPath();

    String getFullPath();

    String[] getDescription();

    void setStringValue(String value, boolean save);

    void setValue(Object value);

    SectionConfiguration getSection();

    String[] getOldPaths();
}