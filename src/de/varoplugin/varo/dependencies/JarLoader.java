package de.varoplugin.varo.dependencies;

import java.io.File;

public interface JarLoader {

	void load(File jar) throws Exception;
}
