package de.cuuky.varo.recovery.recoveries;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

import de.cuuky.cfw.utils.JavaUtils;
import de.cuuky.varo.Main;
import de.cuuky.varo.recovery.FileZipper;

public class VaroBackup extends FileZipper {

	private static ArrayList<VaroBackup> backups;

	static {
		backups = new ArrayList<>();

		loadBackups();
	}

	private VaroBackup(File file) {
		super(file);
	}

	public VaroBackup() {
		super(new File("plugins/Varo/backups/" + JavaUtils.getCurrentDateAsFileable() + ".zip"));

		Main.getDataManager().save();

		zip(getFiles("plugins/Varo"), Paths.get("plugins/Varo"));

		backups.add(this);
	}

	private ArrayList<File> getFiles(String path) {
		File pathFile = new File(path);
		ArrayList<File> files = new ArrayList<>();

		for (File file : pathFile.listFiles()) {
			if (file.isDirectory())
				files.addAll(getFiles(file.getPath()));
			else
				files.add(file);
		}

		return files;
	}

	public void delete() {
		zipFile.delete();
		backups.remove(this);
	}

	private static void loadBackups() {
		File file = new File("plugins/Varo/backups/");
		if (!file.isDirectory())
			return;

		for (File listFile : file.listFiles())
			if (listFile.getName().endsWith(".zip"))
				backups.add(new VaroBackup(listFile));
	}

	public static VaroBackup getBackup(String filename) {
		for (VaroBackup backup : backups) {
			if (!backup.getZipFile().getName().equals(filename.endsWith(".zip") ? filename : filename + ".zip"))
				continue;

			return backup;
		}

		return null;
	}

	public static ArrayList<VaroBackup> getBackups() {
		return backups;
	}
}