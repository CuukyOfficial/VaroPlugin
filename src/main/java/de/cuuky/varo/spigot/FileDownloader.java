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
		URL download = new URL(this.link);
		BufferedInputStream in;
		FileOutputStream fout;

		in = new BufferedInputStream(download.openStream());
		fout = new FileOutputStream(path);

		final byte data[] = new byte[1024];
		int count;
		while ((count = in.read(data, 0, 1024)) != -1) {
			fout.write(data, 0, count);
		}

		if (in != null) {
			in.close();
		}
		if (fout != null) {
			fout.close();
		}
	}
}