package de.cuuky.varo.recovery.recoveries;

import java.io.File;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.cuuky.cfw.recovery.FileZipper;
import de.cuuky.varo.Main;

public class VaroBackup extends FileZipper {
    
    private static final DateFormat DATE_FROMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

	private static ArrayList<VaroBackup> backups;

	static {
		backups = new ArrayList<>();

		loadBackups();
	}

	private VaroBackup(File file) {
		super(file);
	}

	public VaroBackup() {
		super(new File("plugins/Varo/backups/" + newFileName()));

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
	
	public static String newFileName() {
        return DATE_FROMAT.format(new Date()) + ".zip";
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