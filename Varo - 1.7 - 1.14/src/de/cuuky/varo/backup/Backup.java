package de.cuuky.varo.backup;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Backup {

	/*
	 * OLD CODE
	 */

	private static final int BUFFER_SIZE = 4096;

	private String date = null;

	public Backup() {
		zip();
	}

	public Backup(String date) {
		this.date = date;
		zip();
	}

	public void zip() {
		Path sourceDir = Paths.get("plugins/Varo");
		File file = new File("plugins/Varo/backups/" + (date != null ? date : getCurrentDate()) + ".zip");
		try {
			File file1 = new File("plugins/Varo/backups");
			if(!file1.isDirectory())
				file1.mkdirs();
			if(!file.exists())
				file.createNewFile();
		} catch(IOException e1) {
			e1.printStackTrace();
		}
		String zipFileName = file.getPath();
		try {
			ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
			Files.walkFileTree(sourceDir, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
					try {
						if(file.getFileName().toString().endsWith(".zip"))
							return FileVisitResult.CONTINUE;

						Path targetFile = sourceDir.relativize(file);
						outputStream.putNextEntry(new ZipEntry(targetFile.toString()));
						byte[] bytes = Files.readAllBytes(file);
						outputStream.write(bytes, 0, bytes.length);
						outputStream.closeEntry();
					} catch(IOException e) {
						e.printStackTrace();
					}
					return FileVisitResult.CONTINUE;
				}
			});
			outputStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date();

		return dateFormat.format(date);
	}

	public static boolean isBackup(String filename) {
		return new File("plugins/Varo/backups/" + filename + ".zip").exists();
	}

	public static boolean unzip(String zipFilePath, String destDirectory) {
		try {
			File destDir = new File(destDirectory);
			if(!destDir.exists())
				destDir.mkdir();
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
			ZipEntry entry = zipIn.getNextEntry();
			while(entry != null) {
				String filePath = destDirectory + File.separator + entry.getName();
				if(!entry.isDirectory()) {
					extractFile(zipIn, filePath);
				} else {
					File dir = new File(filePath);
					dir.mkdir();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<String> getBackups() {
		File file = new File("plugins/Varo/backups/");
		ArrayList<String> temp = new ArrayList<>();
		if(!file.isDirectory())
			return temp;

		for(File listFile : file.listFiles())
			if(listFile.getName().endsWith(".zip"))
				temp.add(listFile.getName());
		return temp;
	}
}
