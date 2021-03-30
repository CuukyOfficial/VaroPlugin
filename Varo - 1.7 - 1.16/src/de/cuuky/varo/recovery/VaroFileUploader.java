package de.cuuky.varo.recovery;

import java.io.File;

import de.cuuky.cfw.recovery.FileUploader;

public class VaroFileUploader extends FileUploader {

	private static final String UPLOAD_URL = "https://api.anonfile.com/upload?token=894b0ea821338221";

	public VaroFileUploader(File file) {
		super(file);
	}

	public String uploadFile() {
		return super.uploadFile(UPLOAD_URL);
	}
}