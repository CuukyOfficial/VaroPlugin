package de.cuuky.varo.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public abstract class VaroLogger {

	protected File file;
	protected FileWriter fw;
	protected ArrayList<String> logs;
	protected PrintWriter pw;
	protected Scanner scanner;

	public VaroLogger(String name, boolean loadPrevious) {
		this.file = new File("plugins/Varo/logs/", name + ".yml");
		this.logs = new ArrayList<String>();

		try {
			if (!file.exists()) {
				new File(file.getParent()).mkdirs();
				file.createNewFile();
			}

			fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
			scanner = new Scanner(file);

			if (loadPrevious)
				while (scanner.hasNextLine())
					logs.add(scanner.nextLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		return dateFormat.format(date);
	}

	public File getFile() {
		return file;
	}

	public ArrayList<String> getLogs() {
		return logs;
	}
}