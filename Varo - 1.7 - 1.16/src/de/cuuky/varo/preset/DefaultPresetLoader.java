package de.cuuky.varo.preset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.cuuky.varo.Main;

public class DefaultPresetLoader {

	public DefaultPresetLoader() {
		copyDefaultPresets();
	}

	private void copyDefaultPresets() {
		try {
			ZipInputStream zip = new ZipInputStream(new FileInputStream(Main.getInstance().getThisFile()));

			ZipEntry e = null;
			while ((e = zip.getNextEntry()) != null) {
				String name = e.getName();
				e.isDirectory();
				if (name.startsWith("presets")) {
					File file = new File("plugins/Varo/" + name);
					if (e.isDirectory()) {
						file.mkdir();
						continue;
					}

					if (!file.exists()) {
						new File(file.getParent()).mkdirs();
						file.createNewFile();
					} else
						continue;

					FileOutputStream out = new FileOutputStream(file);

					byte[] byteBuff = new byte[1024];
					int bytesRead = 0;
					while ((bytesRead = zip.read(byteBuff)) != -1) {
						out.write(byteBuff, 0, bytesRead);
					}

					out.flush();
					out.close();
				}
			}

			zip.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}