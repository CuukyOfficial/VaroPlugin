package de.cuuky.varo.spigot;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownloader {

	protected String link, path;

	public FileDownloader(String link, String path) {
		this.link = link;
		this.path = path;
	}

	public void startDownload() throws IOException {
		URL downloadURL = new URL(this.link);

		try (BufferedInputStream input = new BufferedInputStream(downloadURL.openStream()); FileOutputStream output =  new FileOutputStream(this.path)) {
			byte[] data = new byte[1024];

			int count;
			while ((count = input.read(data, 0, 1024)) != -1) {
				output.write(data, 0, count);
			}
		}
	}
}