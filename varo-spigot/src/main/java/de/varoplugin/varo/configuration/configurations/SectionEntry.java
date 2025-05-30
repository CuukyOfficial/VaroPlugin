package de.varoplugin.varo.configuration.configurations;

public interface SectionEntry {

    Object getDefaultValue();

    Object getDefaultValueToWrite();

    Object getValue();

    Object getValueToWrite();

    String getPath();

    String getFullPath();

    String[] getDescription();

    void setStringValue(String value, boolean save);

    void setValue(Object value);

    SectionConfiguration getSection();

    String[] getOldPaths();
}