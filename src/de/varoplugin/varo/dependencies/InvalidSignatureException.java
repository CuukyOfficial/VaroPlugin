package de.varoplugin.varo.dependencies;

import java.io.File;

public class InvalidSignatureException extends Exception {

	private static final long serialVersionUID = -6342731795741217928L;

	public InvalidSignatureException(File file, String expected, String actual) {
		super(String.format("Invalid signature for file %s (expected: %s, actual: %s)", file.getAbsolutePath(), expected, actual));
	}
}
