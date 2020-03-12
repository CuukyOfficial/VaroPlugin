package de.cuuky.varo.recovery;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileZipper {

	private static final int BUFFER_SIZE = 4096;

	protected File zipFile;

	public FileZipper(File zipFile) {
		this.zipFile = zipFile;
	}

	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		File oldFile = new File(filePath);
		if(oldFile.exists())
			oldFile.delete();
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	public void zip(ArrayList<File> files, Path rootFrom) {
		try {
			File file1 = new File(zipFile.getParent());
			if(!file1.isDirectory())
				file1.mkdirs();
			if(!zipFile.exists())
				zipFile.createNewFile();
		} catch(IOException e1) {
			e1.printStackTrace();
		}

		String zipFileName = zipFile.getPath();
		try {
			ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));

			for(File toZip : files) {
				if(toZip.getName().endsWith(".zip"))
					continue;
				
				try {
					Path orgPath = Paths.get(toZip.getPath());
					Path zipFilePath = rootFrom.relativize(orgPath);
					
					outputStream.putNextEntry(new ZipEntry(zipFilePath.toString()));
					byte[] buffer = Files.readAllBytes(orgPath);
					outputStream.write(buffer, 0, buffer.length);
					outputStream.closeEntry();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			outputStream.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean unzip(String destDirectory) {
		try {
			File destDir = new File(destDirectory);
			if(!destDir.exists())
				destDir.mkdir();
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile));
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
	
	public File getZipFile() {
		return this.zipFile;
	}
}